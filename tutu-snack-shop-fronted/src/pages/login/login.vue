<script setup lang="ts">
import { ref } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
import { postLoginWxMinAPI, postLoginWxMinSimpleAPI } from '@/services/login';
import { useMemberStore } from '@/stores';
import type { LoginResult } from '@/types/member';

// 表单数据
const identifier = ref('');
const password = ref('');

// 获取 code 登录凭证
let code = ''
onLoad(async()=>{
  const res = await wx.login()
  code = res.code
})

// 用户名/密码登录
const handleLogin = async () => {
  if (!identifier.value || !password.value) {
    uni.showToast({
      icon: 'none',
      title: '请输入用户名和密码'
    });
    return;
  }

  try {
    // 构建包含query参数的URL
    const url = `http://127.0.0.1:8000/users/login?identifier=${encodeURIComponent(identifier.value)}&password=${encodeURIComponent(password.value)}`;
    const res = await uni.request({
      url: url,
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      }
    });

    if (res.statusCode === 200) {
  const memberStore = useMemberStore();
  // 显式断言类型
  memberStore.setProfile(res.data as LoginResult);

  uni.showToast({ icon: 'success', title: '登录成功' });
  setTimeout(() => {
    uni.switchTab({ url: '/pages/my/my' });
  }, 500);
} else {
      uni.showToast({
        icon: 'none',
        title: '登录失败，请检查用户名和密码'
      });
    }
  } catch (error) {
    console.error('登录失败:', error);
    uni.showToast({
      icon: 'none',
      title: '网络异常，请稍后重试'
    });
  }
}

// 获取用户手机号码（企业中写法）
const onGetphonenumber: UniHelper.ButtonOnGetphonenumber = async (ev)=>{
  console.log(ev)
  const encryptedData = ev.detail!.encryptedData!
  const iv = ev.detail!.iv!
  const res = await postLoginWxMinAPI({
    code,
    encryptedData,
    iv
  })
  // console.log(res)
  loginSuccess(res.result)
}

// 模拟手机号码快捷登录（开发练习，无需注册企业账号，个人开发者账号即可使用）
const onGetphonenumberSimple = async ()=>{
  const res = await postLoginWxMinSimpleAPI('13111224477')
  loginSuccess(res.result)
}

//封装下面方法，以便给企业写法和个人开发者写法复用
const loginSuccess = (profile: LoginResult)=>{
  // 保存会员信息
  const memberStore = useMemberStore()
  memberStore.setProfile(profile)
  // console.log(res)
  // 成功提示
  uni.showToast({
    icon: 'success',
    title: '登录成功'
  })
  setTimeout(()=>{
    // 页面跳转
    //不能用uni.navigateTo()，因为我的页面是TabBar页面
    uni.switchTab({ url: '/pages/my/my' })
  },500)
}

// 跳转到注册页面
const goToRegister = () => {
  uni.navigateTo({ url: '/pages/login/register' })
}
</script>

<template>
  <view class="viewport">
    <view class="logo">
      <view class="logo-icon">🤖</view>
      <view class="idea-name">构想</view>
    </view>
    <view class="login">
      <!-- 用户名/密码登录表单 -->
      <input 
        class="input" 
        type="text" 
        v-model="identifier" 
        placeholder="请输入用户名/手机号码"
      />
      <input 
        class="input" 
        type="text" 
        :password="true" 
        v-model="password" 
        placeholder="请输入密码"
      />
      <button class="button phone" @tap="handleLogin">登录</button>
      <view class="register-link">
        <text @tap="goToRegister">还没有账号？立即注册</text>
      </view>

      <!-- 小程序端授权登录 -->
      <view class="extra">
        <view class="caption">
          <text>其他登录方式</text>
        </view>
        <view class="options">
          <button class="button phone" open-type="getPhoneNumber" @getphonenumber="onGetphonenumber">
            <text class="icon icon-phone"></text>
            手机号快捷登录
          </button>
          <!-- 通用模拟登录 -->
          <button @tap="onGetphonenumberSimple">
            <text class="icon icon-phone">模拟快捷登录</text>
          </button>
        </view>
      </view>
      <view class="tips">登录/注册即视为你同意《服务条款》和《构想隐私协议》</view>
    </view>
  </view>
</template>

<style lang="scss">
page {
  height: 100%;
}

.viewport {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 20rpx 40rpx;
}

.logo {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-top: 10vh;
}

.logo-icon {
  border-radius: 6px;
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
  width: 60px;
  height: 60px;
  font-size: 40px;
  padding-left: 3px;
}

.idea-logo {
  width: 120rpx;
  height: 120rpx;
  background-color: #6a1b9a;
  border-radius: 20rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 60rpx;
  font-weight: bold;
  color: white;
  margin-bottom: 20rpx;
}

.idea-name {
  font-size: 48rpx;
  font-weight: bold;
  color: #6a1b9a;
}

.login {
  display: flex;
  flex-direction: column;
  height: 60vh;
  padding: 40rpx 20rpx 20rpx;

  .input {
    width: 100%;
    height: 80rpx;
    font-size: 28rpx;
    border-radius: 72rpx;
    border: 1px solid #d1c4e9;
    padding-left: 30rpx;
    margin-bottom: 20rpx;
  }

  .register-link {
    text-align: right;
    margin-top: 10rpx;
    margin-bottom: 30rpx;
    font-size: 26rpx;
    color: #6a1b9a;
  }

  .button {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 80rpx;
    font-size: 28rpx;
    border-radius: 72rpx;
    color: #fff;
    .icon {
      font-size: 40rpx;
      margin-right: 6rpx;
    }
  }

  .phone {
    background-color: #6a1b9a;
  }

  .wechat {
    background-color: #6a1b9a;
  }

  .extra {
    flex: 1;
    padding: 70rpx 70rpx 0;
    .caption {
      width: 440rpx;
      line-height: 1;
      border-top: 1rpx solid #d1c4e9;
      font-size: 26rpx;
      color: #999;
      position: relative;
      text {
        transform: translate(-40%);
        background-color: #fff;
        position: absolute;
        top: -12rpx;
        left: 50%;
      }
    }

    .options {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 70rpx;
      button {
        padding: 0;
        background-color: transparent;
      }
    }

    .icon {
      font-size: 24rpx;
      color: #444;
      display: flex;
      flex-direction: column;
      align-items: center;

      &::before {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 80rpx;
        height: 80rpx;
        margin-bottom: 6rpx;
        font-size: 40rpx;
        border: 1rpx solid #444;
        border-radius: 50%;
      }
    }
    .icon-weixin::before {
  border-color: #6a1b9a;
  color: #6a1b9a;
}
  }
}

.tips {
  position: absolute;
  bottom: 80rpx;
  left: 20rpx;
  right: 20rpx;
  font-size: 22rpx;
  color: #999;
  text-align: center;
}
</style>

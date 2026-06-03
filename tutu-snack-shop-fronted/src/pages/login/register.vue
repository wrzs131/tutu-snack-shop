<script setup lang="ts">
import { ref } from 'vue';

// 表单数据
const nickname = ref('');
const phone = ref('');
const preferences = ref('');
const password = ref('');
const confirmPassword = ref('');

// 注册功能
const handleRegister = async () => {
  // 表单验证
  if (!nickname.value || !phone.value || !preferences.value || !password.value) {
    uni.showToast({
      icon: 'none',
      title: '请填写所有必填字段'
    });
    return;
  }

  if (password.value !== confirmPassword.value) {
    uni.showToast({
      icon: 'none',
      title: '两次输入的密码不一致'
    });
    return;
  }

  try {
    const res = await uni.request({
      url: 'http://127.0.0.1:8000/users/register',
      method: 'POST',
      data: {
        nickname: nickname.value,
        phone: phone.value,
        preferences: preferences.value,
        password: password.value
      },
      header: {
        'Content-Type': 'application/json'
      }
    });

    if (res.statusCode === 201 || res.statusCode === 200) {
      uni.showToast({
        icon: 'success',
        title: '注册成功'
      });
      
      setTimeout(() => {
        uni.navigateBack(); // 返回登录页面
      }, 500);
    } else {
      uni.showToast({
        icon: 'none',
        title: '注册失败，请稍后重试'
      });
    }
  } catch (error) {
    console.error('注册失败:', error);
    uni.showToast({
      icon: 'none',
      title: '网络异常，请稍后重试'
    });
  }
}

// 返回登录页面
const goToLogin = () => {
  uni.navigateBack();
}
</script>

<template>
  <view class="viewport">
    <view class="logo">
      <image
        src="https://pcapi-xiaotuxian-front-devtest.itheima.net/miniapp/images/logo_icon.png"
      ></image>
    </view>
    <view class="register">
      <!-- 注册表单 -->
      <input 
        class="input" 
        type="text" 
        v-model="nickname" 
        placeholder="请输入昵称"
      />
      <input 
        class="input" 
        type="text" 
        v-model="phone" 
        placeholder="请输入手机号"
        :maxlength="11"
      />
      <input 
        class="input" 
        type="text" 
        v-model="preferences" 
        placeholder="请输入口味偏好（如：口味清淡）"
      />
      <input 
        class="input" 
        type="text" 
        :password="true" 
        v-model="password" 
        placeholder="请输入密码"
      />
      <input 
        class="input" 
        type="text" 
        :password="true" 
        v-model="confirmPassword" 
        placeholder="请确认密码"
      />
      <button class="button register-btn" @tap="handleRegister">注册</button>
      <view class="login-link">
        <text @tap="goToLogin">已有账号？立即登录</text>
      </view>
      <view class="tips">登录/注册即视为你同意《服务条款》和《小兔鲜儿隐私协议》</view>
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
  text-align: center;
  image {
    width: 220rpx;
    height: 220rpx;
    margin-top: 15vh;
  }
}

.register {
  display: flex;
  flex-direction: column;
  height: 70vh;
  padding: 40rpx 20rpx 20rpx;

  .input {
    width: 100%;
    height: 80rpx;
    font-size: 28rpx;
    border-radius: 72rpx;
    border: 1px solid #ddd;
    padding-left: 30rpx;
    margin-bottom: 20rpx;
  }

  .login-link {
    text-align: right;
    margin-top: 10rpx;
    margin-bottom: 30rpx;
    font-size: 26rpx;
    color: #28bb9c;
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
  }

  .register-btn {
    background-color: #28bb9c;
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
}
</style>
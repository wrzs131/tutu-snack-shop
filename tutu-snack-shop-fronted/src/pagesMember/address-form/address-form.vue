<script setup lang="ts">
import { getMemberAddressByIdAPI, postMemberAddressAPI, putMemberAddressByIdAPI } from '@/services/address';
import { onLoad } from '@dcloudio/uni-app';
import { ref } from 'vue'

// 表单数据
const form = ref({
  receiver: '', // 收货人
  contact: '', // 联系方式
  fullLocation: '', // 省市区(前端展示)
  provinceCode: '', // 省份编码(后端参数)
  cityCode: '', // 城市编码(后端参数)
  countyCode: '', // 区/县编码(后端参数)
  address: '', // 详细地址
  isDefault: 0, // 默认地址，1为是，0为否
})

//获取页面参数
const query = defineProps<{
  id?: string
}>()

//获取收货地址详情数据
const getMemberAddressByIdData = async () => { 
  if(query.id){
    // 发送请求
    const res = await getMemberAddressByIdAPI(query.id)
    // 把数据合并到表单中
    Object.assign(form.value,res.result)
  }
  
}

//页面加载
onLoad(()=>{
  getMemberAddressByIdData()
})

//动态设置标题
uni.setNavigationBarTitle({title: query.id ? '修改地址' : '新建地址'})

//收集所在区域
const onRegionChange: UniHelper.RegionPickerOnChange = (ev) => {
  console.log(ev.detail);
  //省市区（前端展示）
  //ev.detail.value是数组，而fullLocation是字符串，所以需要.join(' ')方法转成字符串并用空格隔开
  form.value.fullLocation = ev.detail.value.join(' ')
  //省市区（后端参数）
  const [provinceCode,cityCode,countyCode] = ev.detail.code!
  //因为上面只是把省市区的编码给了一个整体变量，需要解构这个变量，并分别把值赋给表单里的省、市、县
  Object.assign(form.value,{provinceCode,cityCode,countyCode})
}

//收集是否设为默认地址
const onSwitchChange: UniHelper.SwitchOnChange = (ev) => {
  //布尔值转数字
  form.value.isDefault = ev.detail.value ? 1 : 0
}

//定义校验规则
const rules: UniHelper.UniFormsRules = {
  receiver: {
    rules: [{required: true, errorMessage: '请输入收货人姓名'}]
  },
  contact: {
    rules: [
      {required: true, errorMessage: '请输入联系方式'},
      //1 - 手机号码必须以1开头
      // [3-9] - 第二位数字必须是3-9之间的任意数字
      // \d{9} - 后面必须跟9个数字（\d表示数字，{9}表示重复9次）
      {pattern: /^1[3-9]\d{9}$/, errorMessage: '手机号格式不正确'}
    ]
  },
  fullLocation: {
    rules: [{required: true, errorMessage: '请选择所在地区'}]
  },
  address: {
    rules: [{required: true, errorMessage: '请选择详细地址'}]
  }
}
// 获取表单组件实例，用于调用表单方法
const formRef = ref<UniHelper.UniFormsInstance>()

//提交表单
const onSubmit = async () => {
  try{
    // 表单校验
    await formRef.value?.validate?.()
    // 校验通过后再发送请求
    //判断当前页面是否有地址 id
    if(query.id){
      //修改地址请求
      await putMemberAddressByIdAPI(query.id,form.value)
    }else{
      // 新建地址请求
      await postMemberAddressAPI(form.value)
    }
    //成功提示
    uni.showToast({
      icon: 'success',
      title: query.id ? '修改成功' : '添加成功',
    })
    //返回上一页
    setTimeout(()=>{
      uni.navigateBack()
    },400)
  }catch(error){
    uni.showToast({
      icon: 'error',
      title: '请填写完整信息'
    })
  }
}
</script>

<template>
  <view class="content">
    <uni-forms :rules="rules" :model="form" ref="formRef">
      <!-- 表单内容 -->
      <uni-forms-item name="receiver" class="form-item">
        <text class="label">收货人</text>
        <input class="input" placeholder="请填写收货人姓名" v-model="form.receiver" />
      </uni-forms-item>
      <uni-forms-item name="contact" class="form-item">
        <text class="label">手机号码</text>
        <input class="input" placeholder="请填写收货人手机号码" v-model="form.contact" />
      </uni-forms-item>
      <uni-forms-item name="fullLocation" class="form-item">
        <text class="label">所在地区</text>
        <!-- 选择地区时，分割成数组，选择好后通过onRegionChange再转换为字符串 -->
        <picker @change="onRegionChange" class="picker" mode="region" :value="form.fullLocation.split(' ')">
          <!-- fullLocation有值时展示，没有则显示请选择省/市/区(县) -->
          <view v-if="form.fullLocation">{{ form.fullLocation }}</view>
          <view v-else class="placeholder">请选择省/市/区(县)</view>
        </picker>
      </uni-forms-item>
      <uni-forms-item name="address" class="form-item">
        <text class="label">详细地址</text>
        <input class="input" placeholder="街道、楼牌号等信息" v-model="form.address" />
      </uni-forms-item>
      <view class="form-item">
        <label class="label">设为默认地址</label>
        <switch @change="onSwitchChange"  class="switch" color="#27ba9b" :checked="form.isDefault === 1" />
      </view>
    </uni-forms>
  </view>
  <!-- 提交按钮 -->
  <button @tap="onSubmit" class="button">保存并使用</button>
  {{ form }}
</template>

<style lang="scss">
page {
  background-color: #f4f4f4;
}

.content {
  margin: 20rpx 20rpx 0;
  padding: 0 20rpx;
  border-radius: 10rpx;
  background-color: #fff;

  .form-item,
  .uni-forms-item {
    display: flex;
    align-items: center;
    min-height: 96rpx;
    padding: 25rpx 10rpx 40rpx;
    background-color: #fff;
    font-size: 28rpx;
    border-bottom: 1rpx solid #ddd;
    position: relative;
    margin-bottom: 0;

    // 调整 uni-forms 样式
    .uni-forms-item__content {
      display: flex;
    }

    .uni-forms-item__error {
      margin-left: 200rpx;
    }

    &:last-child {
      border: none;
    }

    .label {
      width: 200rpx;
      color: #333;
    }

    .input {
      flex: 1;
      display: block;
      height: 46rpx;
    }

    .switch {
      position: absolute;
      right: -20rpx;
      transform: scale(0.8);
    }

    .picker {
      flex: 1;
    }

    .placeholder {
      color: #808080;
    }
  }
}

.button {
  height: 80rpx;
  margin: 30rpx 20rpx;
  color: #fff;
  border-radius: 80rpx;
  font-size: 30rpx;
  background-color: #27ba9b;
}
</style>
<script setup lang="ts">
import { ref } from 'vue'
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'


// 通用设置开关状态
const isElderMode = ref(false)
const isMessageNotification = ref(true)
const isAutoUpdate = ref(true)

// 返回上一页
const goBack = () => {
  uni.navigateBack()
}

// 处理开关变化
const onElderModeChange = (e: any) => {
  isElderMode.value = e.detail.value
}

const onMessageNotificationChange = (e: any) => {
  isMessageNotification.value = e.detail.value
}

const onAutoUpdateChange = (e: any) => {
  isAutoUpdate.value = e.detail.value
}

// 处理设置项点击，显示功能暂未开发弹框
const handleNotDeveloped = () => {
  uni.showToast({
    title: '该功能暂未开发',
    icon: 'none'
  })
}
</script>

<template>
  <!-- 自定义导航栏 -->
  <CustomNavbar/>

  <view class="viewport">
    <!-- 顶部导航栏 -->
    <view class="nav-bar">
      <view class="nav-left" @click="goBack">
        
          <uni-icons type="arrow-left" size="25" color="#333"></uni-icons>
        
      </view>
      <view class="nav-title">设置</view>
      <view class="nav-right"></view>
    </view>
    
    <!-- 通用设置 -->
    <view class="section">
      <view class="section-title">通用设置</view>
      <view class="section-content">
        <!-- 老人模式 -->
        <view class="setting-item">
          <view class="item-left">
            <view class="item-icon orange">👵</view>
            <view class="item-info">
              <view class="item-name">老人模式</view>
              <view class="item-desc">大字体、高对比度、简化操作</view>
            </view>
          </view>
          <view class="item-right">
            <switch 
      :checked="isElderMode" 
      color="#ff9800" 
      @change="onElderModeChange"
    />
          </view>
        </view>
        
        <!-- 消息通知 -->
        <view class="setting-item">
          <view class="item-left">
            <view class="item-icon blue">🔔</view>
            <view class="item-info">
              <view class="item-name">消息通知</view>
              <view class="item-desc">接收订单状态和优惠信息</view>
            </view>
          </view>
          <view class="item-right">
            <switch 
  :checked="isMessageNotification" 
  color="#2196f3" 
  @change="onMessageNotificationChange"
/>
          </view>
        </view>
        
        <!-- 自动更新 -->
        <view class="setting-item">
          <view class="item-left">
            <view class="item-icon green">⬇️</view>
            <view class="item-info">
              <view class="item-name">自动更新</view>
              <view class="item-desc">WiFi下自动更新应用</view>
            </view>
          </view>
          <view class="item-right">
            <switch 
  :checked="isAutoUpdate" 
  color="#4caf50" 
  @change="onAutoUpdateChange"
/>
          </view>
        </view>
        
      </view>
    </view>
    
    <!-- 账户与安全 -->
    <view class="section">
      <view class="section-title">账户与安全</view>
      <view class="section-content">
        <!-- 账户安全 -->
        <view class="setting-item" @click="handleNotDeveloped">
          <view class="item-left">
            <view class="item-icon purple">🔒</view>
            <view class="item-info">
              <view class="item-name">账户安全</view>
              <view class="item-desc">密码、验证码等安全设置</view>
            </view>
          </view>
          <view class="item-right">
            <uni-icons type="right" size="20" color="#8f939c"></uni-icons>
          </view>
        </view>
        
        <!-- 隐私设置 -->
        <view class="setting-item" @click="handleNotDeveloped">
          <view class="item-left">
            <view class="item-icon purple">🛡️</view>
            <view class="item-info">
              <view class="item-name">隐私设置</view>
              <view class="item-desc">数据使用和隐私权限管理</view>
            </view>
          </view>
          <view class="item-right">
            <uni-icons type="right" size="20" color="#8f939c"></uni-icons>
          </view>
        </view>
      </view>
    </view>
    
    <!-- 法律条款 -->
    <view class="section">
      <view class="section-title">法律条款</view>
      <view class="section-content">
        <!-- 隐私政策 -->
        <view class="setting-item" @click="handleNotDeveloped">
          <view class="item-left">
            <view class="item-icon blue">📄</view>
            <view class="item-info">
              <view class="item-name">隐私政策</view>
              <view class="item-desc">了解我们如何保护你的隐私</view>
            </view>
          </view>
          <view class="item-right">
            <uni-icons type="right" size="20" color="#8f939c"></uni-icons>
          </view>
        </view>
        
        <!-- 用户协议 -->
        <view class="setting-item" @click="handleNotDeveloped">
          <view class="item-left">
            <view class="item-icon green">🤝</view>
            <view class="item-info">
              <view class="item-name">用户协议</view>
              <view class="item-desc">查看使用条款和服务协议</view>
            </view>
          </view>
          <view class="item-right">
            <uni-icons type="right" size="20" color="#8f939c"></uni-icons>
          </view>
        </view>
      </view>
    </view>
    
    <!-- 关于 -->
    <view class="section">
      <view class="section-title">关于</view>
      <view class="section-content">
        <!-- 关于我们 -->
        <view class="setting-item">
          <view class="item-left">
            <view class="item-icon purple">ℹ️</view>
            <view class="item-info">
              <view class="item-name">关于我们</view>
              <view class="item-desc">版本信息和公司介绍</view>
            </view>
          </view>
          <view class="item-right">
            <navigator url="/pagesMember/settings/about" hover-class="none" class="arrow">
              <uni-icons type="right" size="20" color="#8f939c"></uni-icons>
            </navigator>
          </view>
        </view>
        
        <!-- 意见反馈 -->
        <view class="setting-item">
          <view class="item-left">
            <view class="item-icon orange">💬</view>
            <view class="item-info">
              <view class="item-name">意见反馈</view>
              <view class="item-desc">帮助我们改进产品</view>
            </view>
          </view>
          <view class="item-right">
            <navigator url="/pagesMember/settings/feedback" hover-class="none" class="arrow">
              <uni-icons type="right" size="20" color="#8f939c"></uni-icons>
            </navigator>
          </view>
        </view>
        
        <!-- 版本信息 -->
        <view class="setting-item">
          <view class="item-left">
            <view class="item-icon blue">📱</view>
            <view class="item-info">
              <view class="item-name">版本信息</view>
              <view class="item-desc">当前版本 v1.0.0</view>
            </view>
          </view>
          <view class="item-right">
            <view class="version-text">v1.0.0</view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
page {
  background-color: #f5f5f5;
  font-size: 28rpx;
  color: #333;
  margin-top: 80px;
}

.viewport {
  min-height: 100vh;
}

/* 顶部导航栏 */
.nav-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 88rpx;
  padding: 0 30rpx;
  background-color: #fff;
  border-bottom: 2rpx solid #f0f0f0;
  position: fixed;
  top: 30px;
  left: 0;
  right: 0;
  z-index: 100;
}

.nav-left,
.nav-right {
  width: 80rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}

.back-icon {
  font-size: 40rpx;
  color: #333;
}

.nav-title {
  flex: 1;
  text-align: center;
  font-size: 36rpx;
  font-weight: 600;
  color: #333;
}

/* 内容区域 */
.section {
  margin: 120rpx 20rpx 30rpx;
  background-color: #fff;
  border-radius: 20rpx;
  overflow: hidden;
}

.section-title {
  font-size: 32rpx;
  font-weight: 600;
  color: #333;
  padding: 30rpx;
  border-bottom: 2rpx solid #f5f5f5;
}

.section-content {
  padding: 0;
}

/* 设置项 */
.setting-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 28rpx 30rpx;
  border-bottom: 2rpx solid #f5f5f5;
}

.setting-item:last-child {
  border-bottom: none;
}

.item-left {
  display: flex;
  align-items: center;
  flex: 1;
}

.item-icon {
  width: 64rpx;
  height: 64rpx;
  border-radius: 16rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 32rpx;
  margin-right: 24rpx;
  color: #fff;
}

.item-icon.orange {
  background-color: #ff9800;
}

.item-icon.blue {
  background-color: #2196f3;
}

.item-icon.green {
  background-color: #4caf50;
}

.item-icon.purple {
  background-color: #9c27b0;
}

.item-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex: 1;
}

.item-name {
  font-size: 32rpx;
  font-weight: 500;
  color: #333;
  margin-bottom: 8rpx;
}

.item-desc {
  font-size: 24rpx;
  color: #999;
}

.item-right {
  display: flex;
  align-items: center;
}

.arrow {
  display: flex;
  align-items: center;
}

.arrow-icon {
  font-size: 36rpx;
  color: #ddd;
}

.version-text {
  font-size: 28rpx;
  color: #999;
}
</style>
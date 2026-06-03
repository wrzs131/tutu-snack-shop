<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'
import { useMemberStore } from '@/stores'
// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()

// 用户统计数据
const stats = {
  orders: 28,
  collections: 12,
  points: 580
}

// 处理服务项点击
const handleServiceClick = (service: any) => {
  if (service.id === 1) {
    uni.showToast({
      title: '该功能暂未开发',
      icon: 'none'
    })
  } else {
    uni.navigateTo({ url: service.url })
  }
}

// 服务列表数据
const services = [
  { id: 1, name: '我的地址', icon: '📍', count: 3, url: '/pagesMember/address/address', description: '管理收货地址' },
  { id: 2, name: '我的收藏', icon: '❤️', count: 4, url: '/pages/collection/collection', description: '收藏的商家和商品' },
  { id: 3, name: '健康数据授权', icon: '🩺', status: '已授权', url: '/pages/assistant/assistant', description: '同步健康数据，获得个性化推荐' }
]

// 账户设置列表
const settings = [
  { id: 1, name: '账户安全', icon: '🔒', url: '/pagesMember/settings/settings', description: '修改密码、绑定手机' },
  { id: 2, name: '隐私设置', icon: '🔐', url: '/pagesMember/settings/settings', description: '管理隐私权限' },
  { id: 3, name: '设置', icon: '⚙️', url: '/pagesMember/settings/settings', description: '应用设置和偏好配置' }
]

// 帮助与反馈列表
const helpItems = [
  { id: 1, name: '帮助中心', icon: '❓', url: '/pagesMember/settings/settings', description: '常见问题和使用指南' },
  { id: 2, name: '意见反馈', icon: '💬', url: '/pagesMember/settings/settings', description: '告诉我们你的建议' }
]

// 退出登录功能
const handleLogout = () => {
  uni.showModal({
    title: '确认退出',
    content: '确定要退出登录吗？',
    success: (res) => {
      if (res.confirm) {
        const memberStore = useMemberStore()
        memberStore.clearProfile()
        uni.showToast({
          icon: 'success',
          title: '退出成功'
        })
        // 跳转到登录页面
        uni.navigateTo({ url: '/pages/login/login' })
      }
    }
  })
}
</script>

<template>
  <!-- 自定义导航栏 -->
  <CustomNavbar/>

  <scroll-view class="viewport" scroll-y enable-back-to-top>
    <!-- 个人资料头部 -->
    <view class="profile-header" :style="{ paddingTop: safeAreaInsets!.top + 'px' }">
      <!-- 个人信息 -->
      <view class="profile-info">
        <image
          class="avatar"
          mode="aspectFill"
          src="https://pcapi-xiaotuxian-front-devtest.itheima.net/miniapp/uploads/avatar_3.jpg"
        ></image>
        <view class="user-info">
          <view class="nickname">美食探索者</view>
          <view class="user-id">ID: 123456789</view>
        </view>
        <navigator url="/pagesMember/settings/settings" hover-class="none" class="edit-btn">
          <text class="edit-icon">✏️</text>
        </navigator>
      </view>
      
      <!-- 统计数据 -->
      <view class="stats">
        <view class="stat-item">
          <navigator url="/pages/order/order" hover-class="none" class="stat-value">{{ stats.orders }}</navigator>
          <view class="stat-label">总订单</view>
        </view>
        <view class="stat-item">
          <navigator url="/pages/collection/collection" hover-class="none" class="stat-value">{{ stats.collections }}</navigator>
          <view class="stat-label">我的收藏</view>
        </view>
        <view class="stat-item">
          <view class="stat-value">{{ stats.points }}</view>
          <view class="stat-label">积分</view>
        </view>
      </view>
    </view>
    
    <!-- 我的服务 -->
    <view class="services-section">
      <view class="section-title">我的服务</view>
      <view class="service-list">
        <view
          v-for="service in services"
          :key="service.id"
          @click="handleServiceClick(service)"
          class="service-item"
        >
          <view class="service-left">
            <view class="service-icon" :class="`icon-${service.id}`">{{ service.icon }}</view>
            <view class="service-info">
              <view class="service-name">{{ service.name }}</view>
              <view class="service-desc">{{ service.description }}</view>
            </view>
          </view>
          <view class="service-right">
            <template v-if="service.count !== undefined">
              <view class="service-count">{{ service.count }}个</view>
            </template>
            <template v-else-if="service.status">
              <view class="service-status">{{ service.status }}</view>
            </template>
            <uni-icons type="right" size="20" color="#8f939c"></uni-icons>
          </view>
        </view>
      </view>
    </view>
    
    <!-- 账户设置 -->
    <view class="settings-section">
      <view class="section-title">账户设置</view>
      <view class="settings-list">
        <navigator
          v-for="setting in settings"
          :key="setting.id"
          :url="setting.url"
          hover-class="none"
          class="setting-item"
        >
          <view class="setting-left">
            <view class="setting-icon">{{ setting.icon }}</view>
            <view class="setting-name">{{ setting.name }}</view>
          </view>
          <uni-icons type="right" size="20" color="#8f939c"></uni-icons>
        </navigator>
      </view>
    </view>
    
    <!-- 帮助与反馈 -->
    <view class="help-section">
      <view class="section-title">帮助与反馈</view>
      <view class="settings-list">
        <navigator
          v-for="helpItem in helpItems"
          :key="helpItem.id"
          :url="helpItem.url"
          hover-class="none"
          class="setting-item"
        >
          <view class="setting-left">
            <view class="setting-icon">{{ helpItem.icon }}</view>
            <view class="setting-name">{{ helpItem.name }}</view>
          </view>
          <uni-icons type="right" size="20" color="#8f939c"></uni-icons>
        </navigator>
      </view>
    </view>
    
    <!-- 退出登录按钮 -->
    <view class="logout-container">
      <button class="logout-button" @tap="handleLogout">退出登录</button>
    </view>
    
  </scroll-view>
</template>

<style lang="scss">
page {
  height: 100%;
  overflow: hidden;
  background-color: #f7f7f8;
  margin-top: 35px;
}

.viewport {
  height: 100vh; /* 显式设置为视口高度 */
  overflow-y: auto;
}

/* 个人资料头部 */
.profile-header {
  background: linear-gradient(135deg, #9c27b0 0%, #673ab7 100%);
  padding: 30rpx 40rpx 60rpx;
  color: #fff;
  border-radius: 0 0 40rpx 40rpx;
  box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.1);
}

/* 个人信息 */
.profile-info {
  display: flex;
  align-items: center;
  margin-bottom: 40rpx;
}

.avatar {
  width: 140rpx;
  height: 140rpx;
  border-radius: 50%;
  border: 4rpx solid rgba(255, 255, 255, 0.3);
  margin-right: 24rpx;
}

.user-info {
  flex: 1;
}

.nickname {
  font-size: 40rpx;
  font-weight: 600;
  margin-bottom: 12rpx;
}

.user-id {
  font-size: 28rpx;
  opacity: 0.9;
}

.edit-btn {
  width: 60rpx;
  height: 60rpx;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.2);
  display: flex;
  justify-content: center;
  align-items: center;
}

.edit-icon {
  font-size: 32rpx;
}

/* 统计数据 */
.stats {
  display: flex;
  justify-content: space-around;
}

.stat-item {
  text-align: center;
}

.stat-value {
  display: block;
  font-size: 48rpx;
  font-weight: 600;
  color: #fff;
  margin-bottom: 8rpx;
}

.stat-label {
  font-size: 28rpx;
  opacity: 0.9;
}

/* 通用区块样式 */
.services-section,
.settings-section,
.orders-section,
.coupon-section {
  margin: 30rpx 20rpx;
  background-color: #fff;
  border-radius: 20rpx;
  padding: 0 30rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.section-title {
  font-size: 32rpx;
  font-weight: 600;
  color: #333;
  padding: 30rpx 0;
  border-bottom: 2rpx solid #f5f5f5;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30rpx 0;
  border-bottom: 2rpx solid #f5f5f5;
}

.view-all {
  font-size: 28rpx;
  color: #999;
}

/* 服务列表 */
.service-list {
  padding: 20rpx 0;
}

.service-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24rpx 0;
  border-bottom: 2rpx solid #f5f5f5;
}

.service-item:last-child {
  border-bottom: none;
}

.service-left {
  display: flex;
  align-items: center;
}

.service-icon {
  width: 80rpx;
  height: 80rpx;
  border-radius: 16rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 44rpx;
  margin-right: 24rpx;
  color: #fff;
}

.icon-1 {
  background-color: #9c27b0;
}

.icon-2 {
  background-color: #e91e63;
}

.icon-3 {
  background-color: #4caf50;
}

.service-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.service-name {
  font-size: 32rpx;
  font-weight: 500;
  color: #333;
  margin-bottom: 8rpx;
}

.service-desc {
  font-size: 24rpx;
  color: #999;
}

.service-right {
  display: flex;
  align-items: center;
}

.service-count {
  font-size: 28rpx;
  color: #999;
  margin-right: 12rpx;
}

.service-status {
  font-size: 28rpx;
  color: #4caf50;
  font-weight: 500;
  margin-right: 12rpx;
}

.arrow-icon {
  font-size: 32rpx;
  color: #ddd;
  margin-left: 12rpx;
}

/* 设置列表 */
.help-section { 
  margin: 30rpx 20rpx;
  background-color: #fff;
  border-radius: 20rpx;
  padding: 0 30rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
  margin-bottom: 20rpx;
}

.settings-list {
  padding: 20rpx 0;
}

.setting-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 28rpx 0;
  border-bottom: 2rpx solid #f5f5f5;
}

.setting-item:last-child {
  border-bottom: none;
}

.setting-left {
  display: flex;
  align-items: center;
}

.setting-icon {
  font-size: 40rpx;
  margin-right: 24rpx;
  color: #ff9800;
}

.setting-name {
  font-size: 32rpx;
  color: #333;
}

/* 退出登录按钮样式 */
.logout-container {
  padding: 20rpx 40rpx;
  margin-top: 0rpx;
  margin-bottom: 40px;
}

.logout-button {
  width: 100%;
  height: 80rpx;
  background-color: #ff4d4f;
  color: #fff;
  font-size: 28rpx;
  border-radius: 40rpx;
  border: none;
}
</style>
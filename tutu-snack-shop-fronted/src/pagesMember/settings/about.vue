<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'

// 返回上一页
const goBack = () => {
  uni.navigateBack()
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
      <view class="nav-title">关于我们</view>
      <view class="nav-right"></view>
    </view>
    
    <!-- 内容区域 -->
    <view class="content">
      <!-- 应用信息 -->
      <view class="app-info">
        <view class="app-logo">🤖</view>
        <view class="app-name">构想</view>
        <view class="app-subtitle">智能零食助手</view>
        <view class="app-version">版本 v1.0.0</view>
      </view>
      
      <!-- 产品介绍 -->
      <view class="product-info">
        <view class="section-header">
          <text class="section-icon">💡</text>
          <text class="section-title">产品介绍</text>
        </view>
        <view class="section-content">
          <text>图图零食店是一款基于AI智能体的移动端电商零食店应用，其智能体“构想”通过自然交互、个性化决策和全流程协同能力，为用户提供智能化的零食店下单服务，让零食店下单体验更高效、更个性化、更愉悦。</text>
        </view>
      </view>
      
      <!-- 核心特色 -->
      <view class="features">
        <view class="section-title">核心特色</view>
        
        <!-- 智能交互 -->
        <view class="feature-item">
          <view class="feature-icon purple">💬</view>
          <view class="feature-content">
            <view class="feature-name">智能交互</view>
            <view class="feature-desc">支持文字、语音、图片等多种自然交互方式</view>
          </view>
        </view>
        
        <!-- 个性化推荐 -->
        <view class="feature-item">
          <view class="feature-icon blue">👤</view>
          <view class="feature-content">
            <view class="feature-name">个性化推荐</view>
            <view class="feature-desc">基于多维度数据为您推荐最适合的美食</view>
          </view>
        </view>
        
        <!-- 全流程自动化 -->
        <view class="feature-item">
          <view class="feature-icon green">⚡</view>
          <view class="feature-content">
            <view class="feature-name">全流程自动化</view>
            <view class="feature-desc">从下单到售后，AI智能体全程辅助</view>
          </view>
        </view>
      </view>
      
      <!-- 公司信息 -->
      <view class="company-info">
        <view class="section-header">
          <text class="section-icon">🏢</text>
          <text class="section-title">公司信息</text>
        </view>
        <view class="info-list">
          <view class="info-item">
            <text class="info-label">公司名称</text>
            <text class="info-value">构想科技有限公司</text>
          </view>
          <view class="info-item">
            <text class="info-label">成立时间</text>
            <text class="info-value">2024年</text>
          </view>
          <view class="info-item">
            <text class="info-label">公司愿景</text>
            <text class="info-value">成为用户首选的智能外卖助手</text>
          </view>
        </view>
      </view>
      
      <!-- 版权信息 -->
      <view class="copyright-info">
        <view class="section-header">
          <text class="section-icon">©️</text>
          <text class="section-title">版权信息</text>
        </view>
        <view class="section-content">
          <text>© 2024 构想科技有限公司</text>
          <text>保留所有权利</text>
          <text>本软件受著作权法和国际条约保护</text>
        </view>
      </view>
      
      <!-- 联系我们 -->
      <view class="contact-info">
        <view class="section-header">
          <text class="section-icon">✉️</text>
          <text class="section-title">联系我们</text>
        </view>
        <view class="contact-list">
          <view class="contact-item">
            <text class="contact-icon">📧</text>
            <text class="contact-text">support@conception.com</text>
          </view>
          <view class="contact-item">
            <text class="contact-icon">🌐</text>
            <text class="contact-text">www.conception.com</text>
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
  margin-top: 30px;
}

.viewport {
  min-height: 100vh;
}

/* 顶部导航栏 */
.nav-bar {
  margin-top: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 88rpx;
  padding: 0 30rpx;
  background-color: #fff;
  border-bottom: 2rpx solid #f0f0f0;
  position: fixed;
  top: 0;
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
.content {
  padding: 120rpx 30rpx 30rpx;
}

/* 应用信息 */
.app-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 60rpx 0;
  background-color: #fff;
  border-radius: 20rpx;
  margin-bottom: 30rpx;
}

.app-logo {
  width: 160rpx;
  height: 160rpx;
  border-radius: 32rpx;
  background: linear-gradient(135deg, #9c27b0 0%, #673ab7 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 80rpx;
  color: #fff;
  margin-bottom: 30rpx;
}

.app-name {
  font-size: 48rpx;
  font-weight: 600;
  color: #333;
  margin-bottom: 12rpx;
}

.app-subtitle {
  font-size: 28rpx;
  color: #999;
  margin-bottom: 16rpx;
}

.app-version {
  font-size: 28rpx;
  color: #999;
  background-color: #f5f5f5;
  padding: 8rpx 24rpx;
  border-radius: 16rpx;
}

/* 通用区块样式 */
.product-info,
.features,
.company-info,
.copyright-info,
.contact-info {
  background-color: #fff;
  border-radius: 20rpx;
  margin-bottom: 30rpx;
  padding: 30rpx;
}

.section-header {
  display: flex;
  align-items: center;
  margin-bottom: 24rpx;
}

.section-icon {
  font-size: 32rpx;
  margin-right: 12rpx;
}

.section-title {
  font-size: 32rpx;
  font-weight: 600;
  color: #333;
  margin-bottom: 24rpx;
}

.section-content {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
  line-height: 1.6;
  color: #666;
}

/* 核心特色 */
.feature-item {
  display: flex;
  align-items: center;
  padding: 24rpx 0;
  border-bottom: 2rpx solid #f5f5f5;
}

.feature-item:last-child {
  border-bottom: none;
}

.feature-icon {
  width: 64rpx;
  height: 64rpx;
  border-radius: 16rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 36rpx;
  color: #fff;
  margin-right: 24rpx;
}

.feature-icon.purple {
  background-color: #9c27b0;
}

.feature-icon.blue {
  background-color: #2196f3;
}

.feature-icon.green {
  background-color: #4caf50;
}

.feature-content {
  flex: 1;
}

.feature-name {
  font-size: 32rpx;
  font-weight: 500;
  color: #333;
  margin-bottom: 8rpx;
}

.feature-desc {
  font-size: 24rpx;
  color: #999;
}

/* 公司信息 */
.info-list {
  display: flex;
  flex-direction: column;
  gap: 28rpx;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 16rpx 0;
  border-bottom: 2rpx solid #f5f5f5;
}

.info-item:last-child {
  border-bottom: none;
}

.info-label {
  font-size: 30rpx;
  color: #666;
  flex: 1;
}

.info-value {
  font-size: 30rpx;
  color: #333;
  font-weight: 500;
  flex: 2;
  text-align: right;
}

/* 联系我们 */
.contact-list {
  display: flex;
  flex-direction: column;
  gap: 28rpx;
}

.contact-item {
  display: flex;
  align-items: center;
}

.contact-icon {
  font-size: 36rpx;
  margin-right: 20rpx;
  width: 40rpx;
}

.contact-text {
  font-size: 30rpx;
  color: #666;
}
</style>
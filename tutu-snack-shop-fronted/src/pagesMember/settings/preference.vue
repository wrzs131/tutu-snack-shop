<script setup lang="ts">
import { ref } from 'vue'

// 返回上一页
const goBack = () => {
  uni.navigateBack()
}

// 滚单偏好设置
const scrollFrequency = ref('medium') // low: 低频率, medium: 中等频率, high: 高频率
const scrollType = ref(['breakfast', 'lunch', 'dinner']) // 早餐、午餐、晚餐
const priceRange = ref('medium') // low: 10-20元, medium: 20-50元, high: 50元以上
const autoScroll = ref(true) // 自动滚单开关

// 保存设置
const saveSettings = () => {
  // 这里可以添加保存设置的逻辑
  uni.showToast({
    title: '设置已保存',
    icon: 'success',
    duration: 2000
  })
}

const onAutoScrollChange = (e: any) => {
  autoScroll.value = e.detail.value
}
</script>

<template>
  <view class="viewport">
    <!-- 顶部导航栏 -->
    <view class="nav-bar">
      <view class="nav-left" @click="goBack">
        <text class="back-icon">←</text>
      </view>
      <view class="nav-title">滚单偏好设置</view>
      <view class="nav-right"></view>
    </view>
    
    <!-- 内容区域 -->
    <view class="content">
      <!-- 滚单频率 -->
      <view class="setting-section">
        <view class="section-title">滚单频率</view>
        <view class="section-content">
          <radio-group v-model="scrollFrequency" class="radio-group">
            <label class="radio-item">
              <radio value="low" color="#9c27b0" />
              <text class="radio-text">低频率</text>
            </label>
            <label class="radio-item">
              <radio value="medium" color="#9c27b0" />
              <text class="radio-text">中等频率</text>
            </label>
            <label class="radio-item">
              <radio value="high" color="#9c27b0" />
              <text class="radio-text">高频率</text>
            </label>
          </radio-group>
          <view class="section-desc">选择你希望收到滚单的频率</view>
        </view>
      </view>
      
      <!-- 滚单类型偏好 -->
      <view class="setting-section">
        <view class="section-title">滚单类型偏好</view>
        <view class="section-content">
          <checkbox-group v-model="scrollType" class="checkbox-group">
            <label class="checkbox-item">
              <checkbox value="breakfast" color="#9c27b0" />
              <text class="checkbox-text">早餐</text>
            </label>
            <label class="checkbox-item">
              <checkbox value="lunch" color="#9c27b0" />
              <text class="checkbox-text">午餐</text>
            </label>
            <label class="checkbox-item">
              <checkbox value="dinner" color="#9c27b0" />
              <text class="checkbox-text">晚餐</text>
            </label>
            <label class="checkbox-item">
              <checkbox value="snack" color="#9c27b0" />
              <text class="checkbox-text">零食</text>
            </label>
            <label class="checkbox-item">
              <checkbox value="drink" color="#9c27b0" />
              <text class="checkbox-text">饮品</text>
            </label>
          </checkbox-group>
          <view class="section-desc">选择你感兴趣的滚单类型</view>
        </view>
      </view>
      
      <!-- 价格范围偏好 -->
      <view class="setting-section">
        <view class="section-title">价格范围偏好</view>
        <view class="section-content">
          <radio-group v-model="priceRange" class="radio-group">
            <label class="radio-item">
              <radio value="low" color="#9c27b0" />
              <text class="radio-text">10-20元</text>
            </label>
            <label class="radio-item">
              <radio value="medium" color="#9c27b0" />
              <text class="radio-text">20-50元</text>
            </label>
            <label class="radio-item">
              <radio value="high" color="#9c27b0" />
              <text class="radio-text">50元以上</text>
            </label>
          </radio-group>
          <view class="section-desc">选择你希望收到的滚单价格范围</view>
        </view>
      </view>
      
      <!-- 自动滚单 -->
      <view class="setting-section">
        <view class="section-title">自动滚单</view>
        <view class="section-content">
          <view class="switch-item">
            <text class="switch-text">开启自动滚单功能</text>
            <switch 
  :checked="autoScroll" 
  color="#9c27b0" 
  @change="onAutoScrollChange"
/>
          </view>
          <view class="section-desc">开启后，系统将根据你的偏好自动为你推送滚单</view>
        </view>
      </view>
      
      <!-- 保存按钮 -->
      <view class="save-button-container">
        <button class="save-button" @click="saveSettings">保存设置</button>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
page {
  background-color: #f5f5f5;
  font-size: 28rpx;
  color: #333;
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

/* 设置区块 */
.setting-section {
  background-color: #fff;
  border-radius: 20rpx;
  margin-bottom: 30rpx;
  padding: 30rpx;
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
}

.section-desc {
  font-size: 24rpx;
  color: #999;
  margin-top: 10rpx;
}

/* 单选按钮组 */
.radio-group {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.radio-item {
  display: flex;
  align-items: center;
  padding: 16rpx 0;
}

.radio-item radio {
  margin-right: 20rpx;
  transform: scale(1.5);
}

.radio-text {
  font-size: 30rpx;
  color: #333;
}

/* 多选按钮组 */
.checkbox-group {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.checkbox-item {
  display: flex;
  align-items: center;
  padding: 16rpx 0;
}

.checkbox-item checkbox {
  margin-right: 20rpx;
  transform: scale(1.5);
}

.checkbox-text {
  font-size: 30rpx;
  color: #333;
}

/* 开关选项 */
.switch-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16rpx 0;
}

.switch-text {
  font-size: 30rpx;
  color: #333;
}

/* 保存按钮 */
.save-button-container {
  margin-top: 50rpx;
  padding: 0 30rpx;
}

.save-button {
  width: 100%;
  height: 90rpx;
  background-color: #9c27b0;
  color: #fff;
  font-size: 32rpx;
  font-weight: 500;
  border-radius: 45rpx;
  border: none;
  display: flex;
  justify-content: center;
  align-items: center;
}

.save-button:active {
  background-color: #7b1fa2;
}
</style>
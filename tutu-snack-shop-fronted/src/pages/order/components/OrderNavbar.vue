<script setup lang="ts">
// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()
import { ref } from 'vue'

// 搜索关键词
const searchKeyword = ref('')

// 搜索事件
const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    uni.navigateTo({
      url: `/pages/search/search?keyword=${encodeURIComponent(searchKeyword.value.trim())}`
    })
  }
}

// 接收外部传入的标题
const props = defineProps<{
  title?: string
  showBack?: boolean
}>()

// 返回按钮点击事件
const handleBack = () => {
  uni.navigateBack()
}
</script>

<template>
  <view class="navbar" :style="{ paddingTop: safeAreaInsets?.top + 'px' }">
    <!-- 如果有标题则显示标题，否则显示logo -->
    <view v-if="props.title" class="navbar-title">
      <view v-if="props.showBack" class="back-button" @click="handleBack">
        <uni-icons type="left" size="32" color="#fff"></uni-icons>
      </view>
      <text>{{ props.title }}</text>
    </view>
    
    
    <!-- 搜索条 -->
    <!-- <view class="search" @click="handleSearch">
      <input 
        v-model="searchKeyword" 
        class="search-input" 
        placeholder="搜索美食或唤醒智能助手..." 
        placeholder-style="color: rgba(255, 255, 255, 0.8)"
        @confirm="handleSearch"
      />
      <text class="icon-scan"></text>
    </view> -->
  </view>
</template>

<style lang="scss">
/* 自定义导航条 */
.navbar {
  background: linear-gradient(135deg, #9c27b0 0%, #673ab7 100%);
  background-size: cover;
  padding-bottom: 15px;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 999; /* 确保在最上层 */
  display: flex;
  flex-direction: column;
  
  // 标题样式
  .navbar-title {
    height: 64rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    font-size: 36rpx;
    font-weight: 500;
    position: relative;
    
    // 返回按钮样式
    .back-button {
      position: absolute;
      left: 30rpx;
      top: 50%;
      transform: translateY(-50%);
      padding: 10rpx;
      cursor: pointer;
    }
  }
  
  // Logo样式
  .logo {
    display: flex;
    align-items: center;
    height: 64rpx;
    padding-left: 30rpx;
    .logo-image {
      width: 166rpx;
      height: 39rpx;
    }
    .logo-text {
      flex: 1;
      line-height: 28rpx;
      color: #fff;
      margin: 2rpx 0 0 20rpx;
      padding-left: 20rpx;
      border-left: 1rpx solid #fff;
      font-size: 26rpx;
    }
  }
  
  // 搜索框样式
  .search {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20rpx 0 26rpx;
    height: 72rpx;
    margin: 20rpx 20rpx 30rpx;
    color: #fff;
    font-size: 28rpx;
    border-radius: 36rpx;
    background-color: rgba(255, 255, 255, 0.5);
    
    .search-input {
      flex: 1;
      height: 100%;
      color: #fff;
      font-size: 28rpx;
      padding-right: 20rpx;
      
      // 输入框占位符样式
      &::placeholder {
        color: rgba(255, 255, 255, 0.8);
      }
    }
    
    .icon-scan {
      font-size: 32rpx;
      padding: 15rpx;
    }
  }
}
</style>

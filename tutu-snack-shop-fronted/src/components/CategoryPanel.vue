<script setup lang="ts">
import { ref, defineProps } from 'vue'
import type { ShopInfo } from '@/types/home.d'
import { baseUrl } from '@/utils/baseUrl'

// 定义props
const props = defineProps<{
  shopInfo: ShopInfo
}>()

// 控制底部弹框显示
const showBottomPopup = ref(false)

// 控制弹框动画
const popupAnimation = ref('')

// 店铺图片URL
const imageUrl = baseUrl

// 切换底部弹框显示状态
const toggleDetails = () => {
  if (showBottomPopup.value) {
    // 关闭弹框，添加动画
    popupAnimation.value = 'hide'
    setTimeout(() => {
      showBottomPopup.value = false
      popupAnimation.value = ''
    }, 300)
  } else {
    // 显示弹框
    showBottomPopup.value = true
    // 延迟添加动画，确保DOM已渲染
    setTimeout(() => {
      popupAnimation.value = 'show'
    }, 10)
  }
  // console.log('切换弹框状态:', showBottomPopup.value)
  // console.log('shopInfo详情:', props.shopInfo)
}

// 跳转到订单页面
const goToOrder = () => {
  uni.switchTab({
    url: '/pages/order/order'
  })
}
</script>

<template>
  <view class="category-panel">
    <!-- 店铺头部 -->
    <view class="shop-header">
      <view class="back-section">
          <image class="header-background" :src="`${imageUrl}/shop-header.jpg`" mode="aspectFill"></image>
      </view>
      <!-- <image class="header-background" src="@/static/images/shop-header.jpg" ></image> -->
      <view class="header-content">
        <view class="headphones-icon">
          <uni-icons type="headphones" size="24" color="white"></uni-icons>
        </view>
        <view class="left-section">
          <image class="shop-logo" :src="`${imageUrl}/shop-header.jpg`" mode="aspectFill"></image>
        </view>
        <view class="shop-details">
          <view class="shop-name-container">
            <view class="shop-name">{{ props.shopInfo.name }}</view>
            <uni-icons 
              :type="'down'" 
              size="10" 
              color="white" 
              class="dropdown-btn" 
              @click="toggleDetails"
            ></uni-icons>
          </view>
        </view>
      </view>
    </view>
    
    <!-- 订单/下单标签 -->
    <view class="tab-bar">
      <view class="tab-item active">下单</view>
    </view>
    
    <!-- 配送信息栏 -->
    <view class="delivery-info">
      <view class="delivery-free">免配送费</view>
      <view class="order-entry" @click="goToOrder">
        <uni-icons type="list" size="20" color="#333"></uni-icons>
        <text class="order-text">我的订单</text>
      </view>
    </view>
    
    <!-- 底部弹框 -->
    <view v-show="showBottomPopup" class="bottom-popup-overlay" @click="toggleDetails">
      <view :class="['bottom-popup', popupAnimation]" @click.stop>
        <view class="popup-header">
          <view class="close-button" @click="toggleDetails">
            <uni-icons type="down" size="12" color="#666"></uni-icons>
          </view>
        </view>
        <view class="popup-content">
          <view class="shop-logo-container">
            <image class="shop-logo-popup" :src="`${imageUrl}/shop-header.jpg`" mode="aspectFill"></image>
          </view>
          <view class="shop-name-popup">{{ props.shopInfo.name }}</view>
          <view class="info-item-popup">
            <uni-icons type="phone" size="20" color="black"></uni-icons>
            <text class="info-text-popup">{{ props.shopInfo.phone }}</text>
          </view>
          <view class="info-item-popup">
            <uni-icons type="location" size="20" color="#333"></uni-icons>
            <text class="info-text-popup">{{ props.shopInfo.address }}</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
// 店铺头部样式
.shop-header {
  position: relative;
  padding: 30rpx;
  min-height: 120rpx;
  overflow: hidden;
  
  .header-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    filter: blur(10rpx);
    z-index: 1;
  }
  
  .header-content {
    position: relative;
    display: flex;
    z-index: 2;
    
    .headphones-icon {
      position: absolute;
      top: 30rpx;
      left: 10rpx;
    }
    
    .left-section {
      //图标位置
      margin-top: 100rpx;
      margin-left: 20rpx;
      margin-right: 20rpx;
      
      .shop-logo {
        width: 90rpx;
        height: 90rpx;
        border-radius: 50%;
        background-color: white;
        box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.2);
      }
    }
    
    .shop-details {
      flex: 1;
      //文字位置
      margin-top: 120rpx;
      
      .shop-name-container {
        display: flex;
        align-items: center;
        gap: 12rpx;
        
        .shop-name {
          font-size: 28rpx;
          font-weight: 600;
          color: white;
          text-shadow: 0 1rpx 2rpx rgba(0, 0, 0, 0.3);
        }
        
        .dropdown-btn {
          cursor: pointer;
          transition: transform 0.3s ease;
          background-color: rgba(255, 255, 255, 0.2);
          border-radius: 50%;
          padding: 8rpx;
          display: flex;
          align-items: center;
          justify-content: center;
        }
      }
      
      .shop-info-details {
        margin-top: 16rpx;
        padding: 16rpx;
        background-color: rgba(0, 0, 0, 0.3);
        border-radius: 8rpx;
        backdrop-filter: blur(10rpx);
        
        .info-item {
          display: flex;
          align-items: center;
          gap: 8rpx;
          margin-bottom: 12rpx;
          
          &:last-child {
            margin-bottom: 0;
          }
          
          .info-text {
            font-size: 20rpx;
            color: white;
            text-shadow: 0 1rpx 2rpx rgba(0, 0, 0, 0.3);
            line-height: 1.4;
          }
        }
      }
    }
    
    .right-icons {
      display: flex;
      align-items: center;
    }
  }
}

// 标签栏样式
.tab-bar {
  display: flex;
  border-bottom: 1rpx solid #f0f0f0;
  
  .tab-item {
    flex: 1;
    text-align: center;
    padding: 20rpx 0;
    font-size: 28rpx;
    color: #666;
    position: relative;
    
    &.active {
      color: #673ab7;
      font-weight: 600;
      
      &::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 40rpx;
        height: 4rpx;
        background-color: #673ab7;
        border-radius: 2rpx;
      }
    }
  }
}

// 配送信息栏样式
.delivery-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20rpx 30rpx;
  background-color: #f9f9f9;
  
  .delivery-free {
    font-size: 24rpx;
    color: #673ab7;
    font-weight: 500;
  }
  
  .order-entry {
    display: flex;
    align-items: center;
    gap: 8rpx;
    font-size: 24rpx;
    color: #333;
  }
}

// 底部弹框样式
.bottom-popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9999;
  display: flex;
  align-items: flex-end;
  animation: fadeIn 0.3s ease;
}

.bottom-popup {
  background-color: white;
  border-radius: 20rpx 20rpx 0 0;
  width: 100%;
  min-height: 400rpx;
  overflow: visible;
  transform: translateY(100%);
}

.bottom-popup.show {
  animation: slideUp 0.3s ease;
}

.bottom-popup.hide {
  animation: slideDown 0.3s ease;
}

.popup-header {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20rpx 30rpx;
  border-bottom: 1rpx solid #f0f0f0;
  
  .close-button {
    cursor: pointer;
    transition: transform 0.3s ease;
    background-color: #f5f5f5;
    padding: 3rpx 55rpx;
    border-radius: 25rpx;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

.popup-content {
  padding: 30rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  
  .shop-logo-container {
    width: 120rpx;
    height: 120rpx;
    border-radius: 50%;
    overflow: hidden;
    margin-bottom: 20rpx;
    
    .shop-logo-popup {
      width: 100%;
      height: 100%;
    }
  }
  
  .shop-name-popup {
    font-size: 28rpx;
    font-weight: 600;
    margin-bottom: 20rpx;
    color: #333;
  }
  
  .info-item-popup {
    display: flex;
    align-items: center;
    gap: 12rpx;
    margin-bottom: 16rpx;
    width: 100%;
    
    .info-text-popup {
      font-size: 24rpx;
      color: #333;
      line-height: 1.4;
    }
  }
}

// 动画效果
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideUp {
  from {
    transform: translateY(100%);
  }
  to {
    transform: translateY(0);
  }
}

.bottom-popup.show {
  animation: slideUp 0.3s ease forwards;
}

@keyframes slideDown {
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(100%);
  }
}

.bottom-popup.hide {
  animation: slideDown 0.3s ease forwards;
}
</style>
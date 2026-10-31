<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'
import { ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import { createOrderAPI } from '@/services/order'
import type { CartItem } from '@/types/cart.d'
import { baseUrl } from '@/utils/baseUrl'

// 购物车数据
const cartItems = ref<CartItem[]>([])
const totalAmount = ref<number>(0)
const shopName = ref<string>('图图零食店')

// 图片URL
const imageUrl = baseUrl

// 加载状态
const loading = ref(false)

// 解析购物车数据
// 小程序端 getCurrentPages() 的页面对象带 options，App 端（app-vue）没有该属性，
// 因此统一改用 onLoad 获取页面参数，三端行为一致
const loadCartData = (raw?: string) => {
  if (!raw) return
  try {
    let text = raw
    try {
      text = decodeURIComponent(raw)
    } catch {
      // 个别平台会自动解码，直接使用原值
    }
    const data = JSON.parse(text)
    cartItems.value = data.items || []
    totalAmount.value = data.totalAmount || 0
  } catch (err) {
    console.error('解析购物车数据失败:', err)
  }
}

onLoad((options) => {
  loadCartData(options?.cartData as string)
})

// 确认订单
const handleConfirmOrder = async () => {
  if (cartItems.value.length === 0) {
    uni.showToast({
      title: '购物车为空',
      icon: 'none'
    })
    return
  }

  loading.value = true
  try {
    // 构建订单参数
    const orderItems = cartItems.value.map(item => ({
      productId: item.productId,
      quantity: item.quantity,
      price: item.price
    }))

    await createOrderAPI({
      totalAmount: totalAmount.value,
      items: orderItems
    })

    uni.showToast({
      title: '下单成功',
      icon: 'success'
    })

    // 延迟跳转到订单页面
    setTimeout(() => {
      uni.switchTab({
        url: '/pages/order/order'
      })
    }, 1500)
  } catch (err) {
    console.error('创建订单失败:', err)
    uni.showToast({
      title: '下单失败',
      icon: 'none'
    })
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <view class="checkout-page">
    <!-- 自定义导航栏 -->
    <CustomNavbar title="结算" :showBack="true" />
    
    <!-- 店铺信息 -->
    <view class="shop-info">
      <view class="shop-name">{{ shopName }}</view>
    </view>
    
    <!-- 商品列表 -->
    <view class="products-section">
      <view class="section-title">商品清单</view>
      <view class="product-list">
        <view v-for="item in cartItems" :key="item.id" class="product-item">
          <image :src="`${imageUrl}/mian.png`" class="product-image"></image>
          <view class="product-info">
            <view class="product-name">{{ item.product?.name || '商品' }}</view>
            <view class="product-meta">
              <text class="product-price">¥{{ item.price }}</text>
              <text class="product-quantity">x{{ item.quantity }}</text>
            </view>
          </view>
        </view>
      </view>
    </view>
    
    <!-- 金额统计 -->
    <view class="amount-section">
      <view class="amount-row">
        <text class="amount-label">商品总额</text>
        <text class="amount-value">¥{{ totalAmount.toFixed(2) }}</text>
      </view>
      <view class="amount-row">
        <text class="amount-label">配送费</text>
        <text class="amount-value">¥0.00</text>
      </view>
      <view class="amount-row total">
        <text class="amount-label">实付金额</text>
        <text class="amount-value total-amount">¥{{ totalAmount.toFixed(2) }}</text>
      </view>
    </view>
    
    <!-- 底部操作栏 -->
    <view class="bottom-bar">
      <button 
        class="confirm-btn" 
        :class="{ disabled: loading }" 
        :disabled="loading"
        @click="handleConfirmOrder"
      >
        {{ loading ? '处理中...' : '确认订单' }}
      </button>
    </view>
  </view>
</template>

<style lang="scss">
page {
  background-color: #f7f7f8;
  padding-bottom: 120rpx;
}

.checkout-page {
  min-height: 100vh;
  padding-top: 0;
}

/* 店铺信息 */
.shop-info {
  background-color: #fff;
  padding: 30rpx;
  margin-bottom: 20rpx;
  
  .shop-name {
    font-size: 32rpx;
    font-weight: 600;
    color: #333;
  }
}

/* 商品列表 */
.products-section {
  background-color: #fff;
  padding: 30rpx;
  margin-bottom: 20rpx;
  
  .section-title {
    margin-top: 20rpx;
    font-size: 40rpx;
    font-weight: 500;
    color: #333;
    margin-bottom: 20rpx;
  }
  
  .product-list {
    .product-item {
      display: flex;
      padding: 20rpx 0;
      border-bottom: 1rpx solid #f5f5f5;
      
      &:last-child {
        border-bottom: none;
      }
      
      .product-image {
        width: 140rpx;
        height: 140rpx;
        border-radius: 12rpx;
        margin-right: 24rpx;
        flex-shrink: 0;
      }
      
      .product-info {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        
        .product-name {
          font-size: 28rpx;
          color: #333;
          margin-bottom: 20rpx;
        }
        
        .product-meta {
          display: flex;
          justify-content: space-between;
          align-items: center;
          
          .product-price {
            font-size: 32rpx;
            font-weight: 600;
            color: #ff4d4f;
          }
          
          .product-quantity {
            font-size: 26rpx;
            color: #666;
          }
        }
      }
    }
  }
}

/* 金额统计 */
.amount-section {
  background-color: #fff;
  padding: 30rpx;
  
  .amount-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16rpx 0;
    
    &.total {
      padding-top: 24rpx;
      border-top: 1rpx solid #f5f5f5;
    }
    
    .amount-label {
      font-size: 28rpx;
      color: #666;
    }
    
    .amount-value {
      font-size: 28rpx;
      color: #333;
      
      &.total-amount {
        font-size: 36rpx;
        font-weight: 600;
        color: #ff4d4f;
      }
    }
  }
}

/* 底部操作栏 */
.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #fff;
  padding: 20rpx 30rpx;
  padding-bottom: calc(20rpx + constant(safe-area-inset-bottom));
  padding-bottom: calc(20rpx + env(safe-area-inset-bottom));
  box-shadow: 0 -2rpx 12rpx rgba(0, 0, 0, 0.05);
  display: flex;
  justify-content: flex-end;
  
  .confirm-btn {
    width: 300rpx;
    height: 88rpx;
    background: linear-gradient(135deg, #ff6b6b 0%, #ff4d4f 100%);
    color: #fff;
    font-size: 32rpx;
    font-weight: 500;
    border-radius: 44rpx;
    border: none;
    line-height: 88rpx;
    padding: 0;
    
    &.disabled {
      opacity: 0.6;
      pointer-events: none;
    }
  }
}
</style>

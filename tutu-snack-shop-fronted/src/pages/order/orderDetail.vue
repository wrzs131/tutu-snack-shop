<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'
import { ref, onMounted } from 'vue'
import { getOrderItemsByOrderIdAPI, updateOrderStatusAPI } from '@/services/order'
import { getShopListAPI } from '@/services/home'
import { addToCartAPI, clearCartAPI } from '@/services/cart'
import type { OrderItemDetail } from '@/types/order.d'

// 订单ID
const orderId = ref(0)

// 订单项列表
const orderItems = ref<OrderItemDetail[]>([])

// 订单信息
const orderInfo = ref({
  id: 0,
  status: '',
  totalAmount: 0,
  createdAt: '',
  shopName: '图图零食店',
  shopImage: '/static/images/shop-header.jpg',
  shopRating: 4.8,
  shopAddress: ''
})

// 配送状态
const deliveryStatus = ref({
  status: '配送中',
  estimatedTime: '15:30',
  steps: [
    { name: '商家接单', completed: true },
    { name: '骑手取餐', completed: true },
    { name: '配送中', completed: true },
    { name: '已送达', completed: false }
  ]
})

// 订单创建时间
const orderCreatedAt = ref('')

// 根据订单创建日期设置配送状态
// 2026-03-22 之前的订单 → 已送达
// 2026-03-22 之后的订单 → 待确认
const updateDeliveryStatus = () => {
  if (!orderCreatedAt.value) {
    // 默认：配送中状态
    deliveryStatus.value = {
      status: '配送中',
      estimatedTime: '15:30',
      steps: [
        { name: '商家接单', completed: true },
        { name: '骑手取餐', completed: true },
        { name: '配送中', completed: true },
        { name: '已送达', completed: false }
      ]
    }
    return
  }

  // 解析订单创建日期，格式可能是 2026-03-16 或 2026-03-16 19:01:59
  const orderDateStr = orderCreatedAt.value.split(' ')[0] // 只取日期部分
  const orderDate = new Date(orderDateStr)
  const cutoffDate = new Date('2026-03-22')

  if (orderDate < cutoffDate) {
    // 2026-03-22 之前 → 已送达：所有步骤完成
    deliveryStatus.value = {
      status: '已送达',
      estimatedTime: '15:30',
      steps: [
        { name: '商家接单', completed: true },
        { name: '骑手取餐', completed: true },
        { name: '配送中', completed: true },
        { name: '已送达', completed: true }
      ]
    }
  } else {
    // 2026-03-22 之后 → 待确认：所有步骤未完成
    deliveryStatus.value = {
      status: '待确认',
      estimatedTime: '15:30',
      steps: [
        { name: '商家接单', completed: false },
        { name: '骑手取餐', completed: false },
        { name: '配送中', completed: false },
        { name: '已送达', completed: false }
      ]
    }
  }
}

// 获取订单项
const fetchOrderItems = async () => {
  try {
    const response = await getOrderItemsByOrderIdAPI(orderId.value)
    console.log('订单项数据:', response.data)
    orderItems.value = response.data
    // 根据订单创建日期更新配送状态
    updateDeliveryStatus()
  } catch (err) {
    console.error('获取订单项失败:', err)
  }
}

// 获取店铺信息
const fetchShopInfo = async () => {
  try {
    const response = await getShopListAPI()
    console.log('店铺信息数据:', response.data)
    if (response.data.length > 0) {
      const shopInfo = response.data[0]
      orderInfo.value.shopName = shopInfo.name
      // orderInfo.value.shopImage = shopInfo.image || '/static/images/shop-header.jpg'
      // orderInfo.value.shopRating = shopInfo.rating || 4.8
      orderInfo.value.shopAddress = shopInfo.address
    }
  } catch (err) {
    console.error('获取店铺信息失败:', err)
  }
}

// 组件挂载时获取数据
onMounted(() => {
  // 从路由参数中获取订单ID和创建时间
  try {
    const pages = getCurrentPages()
    if (pages.length > 0) {
      const currentPage = pages[pages.length - 1] as any
      console.log('当前页面:', currentPage)
      console.log('页面参数:', currentPage.options)
      orderId.value = Number(currentPage.options.orderId) || 0
      orderCreatedAt.value = decodeURIComponent(currentPage.options.createdAt || '')
      console.log('订单ID:', orderId.value)
      console.log('订单创建时间:', orderCreatedAt.value)
      
      // 获取订单项数据
      if (orderId.value) {
        fetchOrderItems()
      } else {
        console.error('订单ID获取失败')
      }
    } else {
      console.error('获取页面实例失败')
    }
  } catch (err) {
    console.error('获取订单参数失败:', err)
  }
  
  // 获取店铺信息
  fetchShopInfo()
})

// 查看配送地图
const viewDeliveryMap = () => {
  console.log('查看配送地图')
}

// 催单
const urgeOrder = () => {
  uni.showToast({
    title: '已催单',
    icon: 'success'
  })
}

// 申请退款
const applyRefund = async () => {
  try {
    await updateOrderStatusAPI(orderId.value, '已取消')
    
    uni.showToast({
      title: '已退款',
      icon: 'success'
    })
    
    setTimeout(() => {
      uni.navigateBack()
    }, 1500)
  } catch (err) {
    console.error('申请退款失败:', err)
    uni.showToast({
      title: '操作失败',
      icon: 'none'
    })
  }
}

// 再次购买
const buyAgain = async () => {
  try {
    // 1. 清空购物车
    await clearCartAPI()
    
    // 2. 将订单中的商品按相同数量添加到购物车
    for (const item of orderItems.value) {
      await addToCartAPI({
        productId: item.productId,
        quantity: item.quantity,
        price: item.price
      })
    }
    
    // 3. 提示成功并跳转到首页
    uni.showToast({
      title: '已加入购物车',
      icon: 'success'
    })
    
    setTimeout(() => {
      uni.switchTab({
        url: '/pages/index/index'
      })
    }, 1500)
  } catch (err) {
    console.error('再来一单失败:', err)
    uni.showToast({
      title: '操作失败',
      icon: 'none'
    })
  }
}
</script>

<template>
  <!-- 自定义导航栏 -->
  <CustomNavbar title="订单详情" :showBack="true" />
  
  <!-- 订单状态 -->
  <view class="order-status-section">
    <view class="status-content">
      <view class="status-title">{{ deliveryStatus.status }}</view>
      <view class="status-subtitle" v-if="deliveryStatus.status === '已送达'">商品已送达，感谢您的支持</view>
      <view class="status-subtitle" v-else-if="deliveryStatus.status === '待确认'">等待商家接单确认</view>
      <view class="status-subtitle" v-else>预计{{ deliveryStatus.estimatedTime }}送达</view>
    </view>
    <view class="status-icon">
      <view class="bike-icon">
        <uni-icons type="navigate" size="35" color="#666"></uni-icons>
      </view>
    </view>
  </view>
  
  <!-- 配送进度 -->
  <view class="delivery-progress">
    <view class="progress-steps">
      <view v-for="(step, index) in deliveryStatus.steps" :key="index" class="step-item">
        <view :class="['step-dot', { 'completed': step.completed }]"></view>
        <view :class="['step-line', { 'completed': index < deliveryStatus.steps.length - 1 ? deliveryStatus.steps[index + 1].completed : step.completed }]"></view>
        <view :class="['step-text', { 'completed': step.completed }]">{{ step.name }}</view>
      </view>
    </view>
  </view>
  
  <!-- 店铺信息 -->
  <view class="shop-info-section">
    <view class="shop-info">
      <image :src="orderInfo.shopImage" class="shop-image"></image>
      <view class="shop-details">
        <view class="shop-name">{{ orderInfo.shopName }}</view>
        <view class="shop-rating">
          <uni-icons type="star" size="16" color="#ffd700"></uni-icons>
          <text class="rating-text">{{ orderInfo.shopRating }}</text>
        </view>
        <view class="shop-address">{{ orderInfo.shopAddress }}</view>
      </view>
      <view class="shop-phone">
        <uni-icons type="phone" size="24" color="#666"></uni-icons>
      </view>
    </view>
  </view>
  
  <!-- 商品列表 -->
  <view class="items-section">
    <view class="section-title">商品</view>
    <view class="item-list">
      <view v-for="item in orderItems" :key="item.id" class="item">
        <image :src="`http://localhost:8080/mian.png`" class="item-image"></image>
        <view class="item-details">
          <view class="item-name">{{ item.product.name }}</view>
          <!-- <view class="item-desc">微辣，不要香菜</view> -->
        </view>
        <view class="item-price">
          <view class="price">¥{{ item.product.price }}</view>
          <view class="quantity">x{{ item.quantity }}</view>
        </view>
      </view>
    </view>
  </view>
  
  <!-- 操作按钮 -->
  <view class="action-buttons">
    <button class="action-btn map-btn" @click="viewDeliveryMap">
      <uni-icons type="location-filled" size="12" color="#666"></uni-icons>
      查看配送地图
    </button>
    <button class="action-btn urgent-btn" @click="urgeOrder">
      <uni-icons type="notification-filled" size="12" color="#fff"></uni-icons>
      催单
    </button>
  </view>
  
  <!-- 底部操作按钮 -->
  <view class="bottom-actions">
    <button class="bottom-btn refund-btn" @click="applyRefund">
      <view class="re-icon" style="transform: scaleX(-1);display: inline-block;">
      <uni-icons type="refreshempty" size="12" color="#333"></uni-icons>
      </view>
      申请退款
    </button>
    <button class="bottom-btn buy-again-btn" @click="buyAgain">
      <uni-icons type="refreshempty" size="12" color="#fff"></uni-icons>
      再次购买
    </button>
  </view>
</template>

<style lang="scss">
page {
  background-color: #f5f5f5;
  font-size: 28rpx;
  color: #333;
  margin-top: 60px;
  padding-bottom: 100rpx;
}

/* 订单状态 */
.order-status-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  padding: 30rpx;
  margin-bottom: 20rpx;
  
  .status-content {
    .status-title {
      font-size: 32rpx;
      font-weight: 600;
      color: #333;
      margin-bottom: 8rpx;
    }
    
    .status-subtitle {
      font-size: 24rpx;
      color: #666;
    }
  }
  
  .status-icon {
    .bike-icon {
      width: 80rpx;
      height: 80rpx;
      background-color: #f0f0f0;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
}

/* 配送进度 */
.delivery-progress {
  background-color: #fff;
  padding: 30rpx;
  margin-bottom: 20rpx;
  
  .progress-steps {
    display: flex;
    align-items: center;
    justify-content: space-between;
    
    .step-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      flex: 1;
      
      .step-dot {
        width: 20rpx;
        height: 20rpx;
        border-radius: 50%;
        background-color: #e0e0e0;
        margin-bottom: 8rpx;
        
        &.completed {
          background-color: #27ba9b;
        }
      }
      
      .step-line {
        width: 100%;
        height: 2rpx;
        background-color: #e0e0e0;
        position: relative;
        top: -10rpx;
        margin-top: -2rpx;
        
        &.completed {
          background-color: #27ba9b;
        }
      }
      
      .step-text {
        font-size: 20rpx;
        color: #999;
        
        &.completed {
          color: #27ba9b;
        }
      }
    }
  }
}

/* 店铺信息 */
.shop-info-section {
  background-color: #fff;
  padding: 30rpx;
  margin-bottom: 20rpx;
  
  .shop-info {
    display: flex;
    align-items: center;
    
    .shop-image {
      width: 100rpx;
      height: 100rpx;
      border-radius: 12rpx;
      margin-right: 20rpx;
    }
    
    .shop-details {
      flex: 1;
      
      .shop-name {
        font-size: 32rpx;
        font-weight: 500;
        color: #333;
        margin-bottom: 8rpx;
      }
      
      .shop-rating {
        display: flex;
        align-items: center;
        margin-bottom: 8rpx;
        
        .rating-text {
          font-size: 24rpx;
          color: #666;
          margin-left: 8rpx;
        }
      }
      
      .shop-address {
        font-size: 24rpx;
        color: #666;
      }
    }
    
    .shop-phone {
      width: 60rpx;
      height: 60rpx;
      background-color: #f0f0f0;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
}

/* 商品列表 */
.items-section {
  background-color: #fff;
  padding: 30rpx;
  margin-bottom: 20rpx;
  
  .section-title {
    font-size: 30rpx;
    font-weight: 500;
    color: #333;
    margin-bottom: 20rpx;
  }
  
  .item-list {
    .item {
      display: flex;
      align-items: center;
      margin-bottom: 20rpx;
      
      .item-image {
        width: 100rpx;
        height: 100rpx;
        border-radius: 12rpx;
        margin-right: 20rpx;
      }
      
      .item-details {
        flex: 1;
        
        .item-name {
          font-size: 28rpx;
          color: #333;
          margin-bottom: 8rpx;
        }
        
        .item-desc {
          font-size: 24rpx;
          color: #999;
        }
      }
      
      .item-price {
        text-align: right;
        
        .price {
          font-size: 28rpx;
          font-weight: 500;
          color: #333;
          margin-bottom: 8rpx;
        }
        
        .quantity {
          font-size: 24rpx;
          color: #999;
        }
      }
    }
  }
}

/* 操作按钮 */
.action-buttons {
  display: flex;
  gap: 20rpx;
  padding: 0 30rpx;
  margin-bottom: 20rpx;
  
  .action-btn {
    flex: 1;
    padding: 20rpx;
    border-radius: 12rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10rpx;
    font-size: 28rpx;
    border: none;
    
    &.map-btn {
      background-color: #fff;
      color: #666;
    }
    
    &.urgent-btn {
      background-color: #ff6b6b;
      color: #fff;
    }
  }
}

/* 底部操作按钮 */
.bottom-actions {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  gap: 20rpx;
  padding: 20rpx 30rpx;
  background-color: #fff;
  box-shadow: 0 -2rpx 12rpx rgba(0, 0, 0, 0.05);
  
  .bottom-btn {
    flex: 1;
    padding: 24rpx;
    border-radius: 12rpx;
    font-size: 28rpx;
    border: none;
    
    &.refund-btn {
      background-color: #f0f0f0;
      color: #333;
    }
    
    &.buy-again-btn {
      background-color: #27ba9b;
      color: #fff;
    }
  }
}
</style>
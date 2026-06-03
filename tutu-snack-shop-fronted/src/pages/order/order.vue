<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'
import { ref, computed, onMounted } from 'vue'
import { onShow } from '@dcloudio/uni-app'
import { getOrderListAPI, getOrderItemsByOrderIdAPI } from '@/services/order'
import { addToCartAPI, clearCartAPI } from '@/services/cart'
import type { OrderItem, OrderItemDetail, OrderQueryParam } from '@/types/order.d'

// 状态筛选标签
const filterTabs = [
  { name: '全部', value: '' },
  { name: '待确认', value: '待确认' },
  { name: '配送中', value: '配送中' },
  { name: '已完成', value: '已完成' },
  { name: '已取消', value: '已取消' }
]

// 当前选中的筛选标签索引
const currentFilterIndex = ref(0)

// 订单列表
const orders = ref<OrderItem[]>([])

// 订单项映射，key为订单ID，value为订单项列表
const orderItemsMap = ref<Record<number, OrderItemDetail[]>>({})

// 加载状态
const loading = ref(false)

// 错误信息
const error = ref('')

// 根据筛选条件过滤后的订单列表
const filteredOrders = computed(() => {
  const currentStatus = filterTabs[currentFilterIndex.value].value
  if (!currentStatus) {
    return orders.value
  }
  return orders.value.filter(order => order.status === currentStatus)
})

// 切换筛选标签
const switchFilter = (index: number) => {
  currentFilterIndex.value = index
}

// 获取订单列表
const fetchOrders = async () => {
  loading.value = true
  error.value = ''
  try {
    const params: OrderQueryParam = {
      page: 1,
      pageSize: 10,
      // id: 0,
      begin: '',
      end: ''
    }
    console.log('请求订单列表参数:', params)
    const response = await getOrderListAPI(params)
    console.log('订单列表响应:', response)
    console.log('订单列表数据:', response.data)
    console.log('订单列表rows:', response.data.rows)
    orders.value = response.data.rows
    console.log('最终订单列表:', orders.value)
    // 为每个订单获取订单项
    for (const order of orders.value) {
      await fetchOrderItems(order.id)
    }
  } catch (err) {
    error.value = '获取订单列表失败'
    console.error('获取订单列表失败:', err)
  } finally {
    loading.value = false
    console.log('订单列表加载完成')
  }
}

// 获取订单项
const fetchOrderItems = async (orderId: number) => {
  try {
    console.log('请求订单项参数:', orderId)
    const response = await getOrderItemsByOrderIdAPI(orderId)
    console.log(`订单项响应(${orderId}):`, response)
    console.log(`订单项数据(${orderId}):`, response.data)
    orderItemsMap.value[orderId] = response.data
    console.log(`最终订单项(${orderId}):`, orderItemsMap.value[orderId])
  } catch (err) {
    console.error(`获取订单${orderId}的订单项失败:`, err)
  }
}

onMounted(() => {
  fetchOrders()
})

onShow(() => {
  fetchOrders()
})

// 查看订单详情
const viewOrderDetail = (order: OrderItem) => {
  console.log('查看订单详情:', order.id)
  uni.navigateTo({
    url: `/pages/order/orderDetail?orderId=${order.id}&createdAt=${encodeURIComponent(order.createdAt)}`
  })
}

// 再来一单
const reorder = async (order: OrderItem) => {
  try {
    const items = orderItemsMap.value[order.id]
    if (!items || items.length === 0) {
      uni.showToast({
        title: '没有商品',
        icon: 'none'
      })
      return
    }
    
    // 1. 清空购物车
    await clearCartAPI()
    
    // 2. 将订单中的商品按相同数量添加到购物车
    for (const item of items) {
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
  <CustomNavbar title="我的订单" />
  
  <!-- 订单状态筛选 -->
  <view class="order-filter">
    <view 
      v-for="(tab, index) in filterTabs" 
      :key="index" 
      :class="['filter-item', { 'active': currentFilterIndex === index }]"
      @click="switchFilter(index)"
    >
      {{ tab.name }}
    </view>
  </view>
  
  <!-- 订单列表 -->
  <view class="order-list">
    <view v-if="loading" class="loading">加载中...</view>
    <view v-else-if="error" class="error">{{ error }}</view>
    <view v-else-if="filteredOrders.length === 0" class="empty">暂无订单</view>
    <view v-for="order in filteredOrders" :key="order.id" class="order-item">
      <view class="order-header">
        <view class="restaurant-info">
          <image src="/static/images/shop-header.jpg" class="restaurant-image"></image>
          <view class="restaurant-name">图图零食店</view>
        </view>
        <view class="order-status">{{ order.status }}</view>
      </view>
      
      <view class="order-content">
        <view class="order-items">
          <view v-for="(item, index) in orderItemsMap[order.id]" :key="item.id" class="order-item-name">
            {{ item.product.name }} x {{ item.quantity }}
          </view>
        </view>
        <view class="order-footer">
          <view class="order-price">
            合计：¥{{ order.totalAmount }}
          </view>
          <view class="order-time">
            {{ order.createdAt }}
          </view>
        </view>
      </view>
      
      <view class="order-actions">
        <button class="action-btn" @click="viewOrderDetail(order)">查看详情</button>
        <button class="action-btn primary" v-if="order.status === '待支付'">立即支付</button>
        <button class="action-btn primary" v-else-if="order.status === '配送中'">确认收货</button>
        <button class="action-btn" @click="reorder(order)">再来一单</button>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
page {
  // background-color: #f5f5f5;
  // font-size: 28rpx;
  // color: #333;
  margin-top: 60px;
}

.order-filter {
  display: flex;
  background-color: #fff;
  padding: 20rpx 0;
  border-bottom: 1rpx solid #f0f0f0;
  
  .filter-item {
    flex: 1;
    text-align: center;
    font-size: 28rpx;
    color: #666;
    padding: 10rpx 0;
    
    &.active {
      color: #27ba9b;
      border-bottom: 2rpx solid #27ba9b;
    }
  }
}

.order-list {
  padding: 20rpx;
}

.order-item {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.05);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
}

.restaurant-info {
  display: flex;
  align-items: center;
  
  .restaurant-image {
    width: 64rpx;
    height: 64rpx;
    border-radius: 8rpx;
    margin-right: 16rpx;
  }
  
  .restaurant-name {
    font-size: 32rpx;
    font-weight: 500;
    color: #333;
  }
}

.order-status {
  font-size: 28rpx;
  color: #27ba9b;
  font-weight: 500;
}

.order-content {
  border-top: 1rpx solid #f0f0f0;
  border-bottom: 1rpx solid #f0f0f0;
  padding: 20rpx 0;
  margin-bottom: 20rpx;
}

.order-items {
  margin-bottom: 16rpx;
}

.order-item-name {
  font-size: 28rpx;
  color: #666;
  margin-bottom: 8rpx;
}

.order-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.order-price {
  font-size: 30rpx;
  font-weight: 500;
  color: #ff4444;
}

.order-time {
  font-size: 26rpx;
  color: #999;
}

.order-actions {
  display: flex;
  justify-content: flex-end;
  gap: 16rpx;
}

.action-btn {
  font-size: 28rpx;
  padding: 12rpx 32rpx;
  border-radius: 20rpx;
  background-color: #f0f0f0;
  color: #333;
  border: none;
  
  &.primary {
    background-color: #27ba9b;
    color: #fff;
  }
}

.empty {
  text-align: center;
  font-size: 28rpx;
  color: #999;
}
</style>
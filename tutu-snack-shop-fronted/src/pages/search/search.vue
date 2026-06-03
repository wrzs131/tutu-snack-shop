<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'
import { ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'

// 搜索关键词
const keyword = ref('')

// 搜索结果数据
const searchResults = ref([
  {
    id: '1',
    name: '麻辣香锅',
    restaurant: '香缘绿麻辣香锅',
    price: 38.5,
    rating: 4.8,
    sales: 1200,
    image: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png'
  },
  {
    id: '2',
    name: '香辣鸡腿堡',
    restaurant: '肯德基',
    price: 18.5,
    rating: 4.6,
    sales: 2500,
    image: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png'
  },
  {
    id: '3',
    name: '黄焖鸡米饭',
    restaurant: '杨铭宇黄焖鸡',
    price: 22.0,
    rating: 4.7,
    sales: 1800,
    image: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png'
  }
])

// 生命周期函数，获取页面参数
onLoad((options: any) => {
  if (options.keyword) {
    keyword.value = decodeURIComponent(options.keyword)
  }
})

// 搜索事件
const handleSearch = () => {
  // 这里可以调用搜索API获取实际数据
  console.log('搜索关键词:', keyword.value)
}
</script>

<template>
  <!-- 自定义导航栏 -->
  <CustomNavbar title="搜索结果" />
  
  <!-- 搜索结果头部 -->
  <view class="search-result-header">
    <view class="result-count">找到 {{ searchResults.length }} 个相关结果</view>
    <view class="sort-options">
      <view class="sort-item active">综合</view>
      <view class="sort-item">销量</view>
      <view class="sort-item">评价</view>
      <view class="sort-item">价格</view>
    </view>
  </view>
  
  <!-- 搜索结果列表 -->
  <view class="search-result-list">
    <view v-for="result in searchResults" :key="result.id" class="result-item">
      <image :src="result.image" class="result-image"></image>
      <view class="result-info">
        <view class="result-title">{{ result.name }}</view>
        <view class="result-restaurant">{{ result.restaurant }}</view>
        <view class="result-rating">
          <text class="star-icon">★</text>
          <text class="rating-value">{{ result.rating }}</text>
          <text class="sales">{{ result.sales }}+月售</text>
        </view>
        <view class="result-price">
          <text class="price-symbol">¥</text>
          <text class="price-value">{{ result.price }}</text>
          <text class="price-unit">起</text>
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
.search-result-header {
  background-color: #fff;
  padding: 20rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.result-count {
  font-size: 28rpx;
  color: #666;
  margin-bottom: 20rpx;
}

.sort-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  
  .sort-item {
    font-size: 28rpx;
    color: #333;
    padding: 10rpx 20rpx;
    border-radius: 20rpx;
    
    &.active {
      background-color: #27ba9b;
      color: #fff;
    }
  }
}

.search-result-list {
  padding: 20rpx;
}

.result-item {
  display: flex;
  background-color: #fff;
  border-radius: 16rpx;
  padding: 20rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.05);
}

.result-image {
  width: 180rpx;
  height: 180rpx;
  border-radius: 12rpx;
  margin-right: 20rpx;
  object-fit: cover;
}

.result-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.result-title {
  font-size: 32rpx;
  font-weight: 500;
  color: #333;
  margin-bottom: 8rpx;
}

.result-restaurant {
  font-size: 26rpx;
  color: #666;
  margin-bottom: 12rpx;
}

.result-rating {
  display: flex;
  align-items: center;
  margin-bottom: 12rpx;
  
  .star-icon {
    color: #ffc107;
    font-size: 24rpx;
    margin-right: 4rpx;
  }
  
  .rating-value {
    font-size: 26rpx;
    color: #ff9800;
    margin-right: 12rpx;
  }
  
  .sales {
    font-size: 24rpx;
    color: #999;
  }
}

.result-price {
  display: flex;
  align-items: baseline;
  
  .price-symbol {
    font-size: 26rpx;
    color: #ff4444;
    margin-right: 4rpx;
  }
  
  .price-value {
    font-size: 36rpx;
    font-weight: 500;
    color: #ff4444;
    margin-right: 4rpx;
  }
  
  .price-unit {
    font-size: 24rpx;
    color: #999;
  }
}
</style>
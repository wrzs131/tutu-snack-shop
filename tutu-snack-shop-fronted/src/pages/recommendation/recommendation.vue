<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'
import { ref } from 'vue'

// 个性化推荐数据
const recommendations = ref([
  {
    id: '1',
    type: 'today-special',
    title: '今日特价',
    items: [
      {
        id: '1-1',
        name: '麻辣香锅',
        restaurant: '香缘绿麻辣香锅',
        originalPrice: 48.5,
        specialPrice: 38.5,
        image: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png'
      },
      {
        id: '1-2',
        name: '香辣鸡腿堡套餐',
        restaurant: '肯德基',
        originalPrice: 69.0,
        specialPrice: 59.0,
        image: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png'
      }
    ]
  },
  {
    id: '2',
    type: 'you-may-like',
    title: '猜你喜欢',
    items: [
      {
        id: '2-1',
        name: '黄焖鸡米饭',
        restaurant: '杨铭宇黄焖鸡',
        price: 22.0,
        rating: 4.7,
        image: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png'
      },
      {
        id: '2-2',
        name: '牛肉拉面',
        restaurant: '兰州拉面',
        price: 18.0,
        rating: 4.5,
        image: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png'
      },
      {
        id: '2-3',
        name: '披萨',
        restaurant: '必胜客',
        price: 88.0,
        rating: 4.6,
        image: 'https://qiniu-web-assets.dcloud.net.cn/unidoc/zh/unicloudlogo.png'
      }
    ]
  }
])
</script>

<template>
  <!-- 自定义导航栏 -->
  <CustomNavbar title="个性化推荐" />
  
  <!-- 推荐内容 -->
  <view class="recommendation-container">
    <!-- 今日特价 -->
    <view v-for="recommend in recommendations" :key="recommend.id" class="recommendation-section">
      <view class="section-header">
        <text class="section-title">{{ recommend.title }}</text>
        <text class="section-more">查看更多</text>
      </view>
      
      <!-- 今日特价样式 -->
      <view v-if="recommend.type === 'today-special'" class="special-list">
        <view v-for="item in recommend.items" :key="item.id" class="special-item">
          <image :src="item.image" class="special-image"></image>
          <view class="special-info">
            <view class="special-name">{{ item.name }}</view>
            <view class="special-restaurant">{{ item.restaurant }}</view>
            <view class="special-price">
              <text class="price-current">¥{{ item.specialPrice }}</text>
              <text class="price-original">¥{{ item.originalPrice }}</text>
            </view>
          </view>
        </view>
      </view>
      
      <!-- 猜你喜欢样式 -->
      <view v-else-if="recommend.type === 'you-may-like'" class="like-list">
        <view v-for="item in recommend.items" :key="item.id" class="like-item">
          <image :src="item.image" class="like-image"></image>
          <view class="like-info">
            <view class="like-name">{{ item.name }}</view>
            <view class="like-restaurant">{{ item.restaurant }}</view>
            <view class="like-rating">
              <text class="star-icon">★</text>
              <text class="rating-value">{{ item.rating }}</text>
            </view>
            <view class="like-price">
              <text class="price-symbol">¥</text>
              <text class="price-value">{{ item.price }}</text>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
.recommendation-container {
  padding: 20rpx;
}

.recommendation-section {
  margin-bottom: 40rpx;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
  
  .section-title {
    font-size: 32rpx;
    font-weight: 500;
    color: #333;
  }
  
  .section-more {
    font-size: 26rpx;
    color: #999;
  }
}

/* 今日特价样式 */
.special-list {
  display: flex;
  gap: 20rpx;
  overflow-x: auto;
  padding-bottom: 10rpx;
  
  &::-webkit-scrollbar {
    display: none;
  }
}

.special-item {
  width: 240rpx;
  background-color: #fff;
  border-radius: 16rpx;
  padding: 16rpx;
  box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.05);
}

.special-image {
  width: 100%;
  height: 160rpx;
  border-radius: 12rpx;
  margin-bottom: 16rpx;
  object-fit: cover;
}

.special-info {
  .special-name {
    font-size: 28rpx;
    font-weight: 500;
    color: #333;
    margin-bottom: 8rpx;
  }
  
  .special-restaurant {
    font-size: 24rpx;
    color: #666;
    margin-bottom: 12rpx;
  }
  
  .special-price {
    display: flex;
    align-items: baseline;
    
    .price-current {
      font-size: 30rpx;
      font-weight: 500;
      color: #ff4444;
      margin-right: 12rpx;
    }
    
    .price-original {
      font-size: 24rpx;
      color: #999;
      text-decoration: line-through;
    }
  }
}

/* 猜你喜欢样式 */
.like-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20rpx;
}

.like-item {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 16rpx;
  box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.05);
}

.like-image {
  width: 100%;
  height: 180rpx;
  border-radius: 12rpx;
  margin-bottom: 16rpx;
  object-fit: cover;
}

.like-info {
  .like-name {
    font-size: 28rpx;
    font-weight: 500;
    color: #333;
    margin-bottom: 8rpx;
  }
  
  .like-restaurant {
    font-size: 24rpx;
    color: #666;
    margin-bottom: 12rpx;
  }
  
  .like-rating {
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
    }
  }
  
  .like-price {
    display: flex;
    align-items: baseline;
    
    .price-symbol {
      font-size: 26rpx;
      color: #ff4444;
      margin-right: 4rpx;
    }
    
    .price-value {
      font-size: 32rpx;
      font-weight: 500;
      color: #ff4444;
    }
  }
}
</style>
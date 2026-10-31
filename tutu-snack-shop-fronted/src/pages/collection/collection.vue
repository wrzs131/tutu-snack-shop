<script setup lang="ts">
import CustomNavbar from '@/pages/index/components/CustomNavbar.vue'
import { ref, onMounted } from 'vue'
import { getProductListAPI } from '@/services/home'
import type { ProductItem } from '@/types/home.d'
import { baseUrl } from '@/utils/baseUrl'

// 商品列表
const productList = ref<ProductItem[]>([])

// 加载状态
const loading = ref(false)

// 商品图片URL
const imageUrl = baseUrl

// 获取商品列表，只显示4个
const fetchProducts = async () => {
  loading.value = true
  try {
    const response = await getProductListAPI()
    if (response.data) {
      productList.value = response.data.slice(0, 4)
    }
  } catch (err) {
    console.error('获取商品列表失败:', err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchProducts()
})
</script>

<template>
  <!-- 自定义导航栏 -->
  <CustomNavbar title="我的收藏" :showBack="true" />

  <view class="viewport">
    <view v-if="loading" class="loading">加载中...</view>
    <view v-else-if="productList.length === 0" class="empty">暂无收藏商品</view>
    <view v-else class="product-list">
      <view
        v-for="product in productList"
        :key="product.id"
        class="product-item"
      >
        <image
          :src="product.image.startsWith('http') ? product.image : `${imageUrl}/mian.png`"
          class="product-image"
          mode="aspectFill"
        />
        <view class="product-info">
          <view class="product-name">{{ product.name }}</view>
          <view class="product-desc">库存{{ product.stock }}份</view>
          <view class="product-price">
            <text class="price-symbol">¥</text>
            <text class="price-value">{{ product.price }}</text>
            <text v-if="product.discount" class="price-discount">{{ product.discount }}</text>
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
  margin-top: 60px;
}

.viewport {
  padding: 30rpx;
}

.loading,
.empty {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
  font-size: 28rpx;
}

.product-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.product-item {
  display: flex;
  background-color: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  align-items: center;
}

.product-image {
  width: 200rpx;
  height: 200rpx;
  border-radius: 12rpx;
  margin-right: 24rpx;
  flex-shrink: 0;
}

.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 200rpx;
}

.product-name {
  font-size: 32rpx;
  font-weight: 500;
  color: #333;
  margin-bottom: 12rpx;
}

.product-desc {
  font-size: 24rpx;
  color: #999;
  margin-bottom: 16rpx;
}

.product-price {
  display: flex;
  align-items: baseline;
}

.price-symbol {
  font-size: 24rpx;
  color: #ff4444;
  font-weight: 500;
}

.price-value {
  font-size: 40rpx;
  color: #ff4444;
  font-weight: bold;
  margin-right: 16rpx;
}

.price-discount {
  font-size: 24rpx;
  color: #fff;
  background-color: #ff4444;
  padding: 4rpx 12rpx;
  border-radius: 6rpx;
}
</style>

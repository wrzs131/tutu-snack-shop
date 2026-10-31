<script setup lang="ts">
import CategoryPanel from '@/components/CategoryPanel.vue'
import { ref, computed, onMounted } from 'vue'
import { getCategoryListAPI, getCategoryProductsAPI, getShopListAPI } from '@/services/home'
import { getCartListAPI, addToCartAPI, updateCartAPI, deleteFromCartAPI, clearCartAPI } from '@/services/cart'
import type { CategoryDataItem, ProductItem, ShopInfo } from '@/types/home.d'
import type { CartItem, CartData } from '@/types/cart.d'
import { baseUrl } from '@/utils/baseUrl'

// 店铺信息（底部营业时间使用）
const shopInfo = ref<ShopInfo>({
  id: 1,
  name: '图图零食店',
  logo: '🐱',
  status: '休息中',
  businessHours: '10:00-次日1:00',
  phone: '',
  address: '',
  createdAt: '',
  updatedAt: ''
})

// 控制商品详情弹框显示
const showProductPopup = ref(false)

// 控制商品详情弹框动画
const productPopupAnimation = ref('')

// 当前选中的商品
const selectedProduct = ref<any>(null)

// 分类数据
const categories = ref<CategoryDataItem[]>([])

// 当前选中分类
const selectedCategory = ref<number>(1)

// 商品数据
const products = ref<Record<number, ProductItem[]>>({})

// 扁平商品列表（用于快速查找）
const allProducts = ref<ProductItem[]>([])

// 加载状态
const loading = ref(false)

// 错误信息
const error = ref('')

// 搜索关键词
const searchKeyword = ref('')

// 商品图片URL（与请求地址保持一致，App 端自动切换到模拟器可访问的主机地址）
const imageUrl = baseUrl

// 购物车相关状态
const showCartPopup = ref(false)
const cartData = ref<CartData>({
  cartList: [],
  totalAmount: 0
})
const cartPopupAnimation = ref('')

// 处理购物车数据，补充商品信息
const processedCartList = computed(() => {
  return cartData.value.cartList.map(item => {
    // 从扁平商品列表中查找对应的商品
    const product = allProducts.value.find(p => p.id === item.productId)
    return {
      ...item,
      product: product || item.product
    }
  })
})

// 获取购物车列表
const fetchCartList = async () => {
  try {
    const response = await getCartListAPI()
    if (response.code === 1 && response.data) {
      cartData.value = response.data
    }
  } catch (err) {
    console.error('获取购物车失败:', err)
  }
}

// 添加商品到购物车
const handleAddToCart = async (product: ProductItem, event: Event) => {
  event.stopPropagation()
  try {
    await addToCartAPI({
      productId: product.id,
      quantity: 1,
      price: product.price
    })
    await fetchCartList()
    uni.showToast({
      title: '已加入购物车',
      icon: 'success'
    })
  } catch (err) {
    console.error('添加购物车失败:', err)
    uni.showToast({
      title: '添加失败',
      icon: 'none'
    })
  }
}

// 从商品详情添加到购物车
const handleAddToCartFromDetail = async () => {
  if (!selectedProduct.value) return
  
  try {
    await addToCartAPI({
      productId: selectedProduct.value.id,
      quantity: 1,
      price: selectedProduct.value.price
    })
    await fetchCartList()
    closeProductPopup()
    uni.showToast({
      title: '已加入购物车',
      icon: 'success'
    })
  } catch (err) {
    console.error('添加购物车失败:', err)
    uni.showToast({
      title: '添加失败',
      icon: 'none'
    })
  }
}

// 修改购物车商品数量
const handleUpdateQuantity = async (item: CartItem, change: number) => {
  const newQuantity = item.quantity + change
  if (newQuantity < 1) return
  
  try {
    await updateCartAPI({
      id: item.id,
      productId: item.productId,
      quantity: newQuantity,
      price: item.price
    })
    await fetchCartList()
  } catch (err) {
    console.error('更新购物车失败:', err)
  }
}

// 删除购物车商品
const handleDeleteItem = async (item: CartItem) => {
  try {
    await deleteFromCartAPI(item.id)
    await fetchCartList()
    uni.showToast({
      title: '已删除',
      icon: 'success'
    })
  } catch (err) {
    console.error('删除失败:', err)
  }
}

// 清空购物车
const handleClearCart = async () => {
  uni.showModal({
    title: '确认清空',
    content: '确定要清空购物车吗？',
    success: async (res) => {
      if (res.confirm) {
        try {
          await clearCartAPI()
          await fetchCartList()
          uni.showToast({
            title: '已清空',
            icon: 'success'
          })
        } catch (err) {
          console.error('清空购物车失败:', err)
        }
      }
    }
  })
}

// 去结算
const handleCheckout = () => {
  if (cartData.value.cartList.length === 0) {
    uni.showToast({
      title: '购物车为空',
      icon: 'none'
    })
    return
  }
  
  // 构建传递给结算页面的数据
  const checkoutData = {
    items: processedCartList.value,
    totalAmount: cartData.value.totalAmount
  }
  
  // 关闭购物车弹窗
  closeCartPopup()
  
  // 跳转到结算页面
  uni.navigateTo({
    url: `/pages/order/checkout?cartData=${encodeURIComponent(JSON.stringify(checkoutData))}`
  })
}

// 打开购物车弹窗
const openCartPopup = async () => {
  await fetchCartList()
  showCartPopup.value = true
  setTimeout(() => {
    cartPopupAnimation.value = 'show'
  }, 10)
}

// 关闭购物车弹窗
const closeCartPopup = () => {
  cartPopupAnimation.value = 'hide'
  setTimeout(() => {
    showCartPopup.value = false
    cartPopupAnimation.value = ''
  }, 300)
}

// 购物车商品总数
const cartItemCount = computed(() => {
  return cartData.value.cartList.reduce((total, item) => total + item.quantity, 0)
})

// 处理商品点击事件
const handleProductClick = (product: ProductItem) => {
  selectedProduct.value = product
  showProductPopup.value = true
  // 延迟添加动画，确保DOM已渲染
  setTimeout(() => {
    productPopupAnimation.value = 'show'
  }, 10)
}

// 关闭商品详情弹框
const closeProductPopup = () => {
  productPopupAnimation.value = 'hide'
  setTimeout(() => {
    showProductPopup.value = false
    productPopupAnimation.value = ''
    selectedProduct.value = null
  }, 300)
}

// 切换分类
const changeCategory = async (categoryId: number) => {
  selectedCategory.value = categoryId
  await fetchProducts(categoryId)
}

// 获取当前分类的商品
const currentProducts = computed(() => {
  return products.value[selectedCategory.value] || []
})

// 获取分类列表
const fetchCategories = async () => {
  loading.value = true
  error.value = ''
  try {
    const response = await getCategoryListAPI()
    categories.value = response.data
    if (categories.value.length > 0) {
      selectedCategory.value = categories.value[0].id
      await fetchProducts(categories.value[0].id)
    }
  } catch (err) {
    error.value = '获取分类列表失败'
    console.error('获取分类列表失败:', err)
  } finally {
    loading.value = false
  }
}

// 获取分类商品列表
const fetchProducts = async (categoryId: number) => {
  loading.value = true
  error.value = ''
  try {
    const response = await getCategoryProductsAPI(categoryId)
    products.value[categoryId] = response.data
    
    // 更新扁平商品列表，避免重复
    response.data.forEach((product: ProductItem) => {
      const existingIndex = allProducts.value.findIndex(p => p.id === product.id)
      if (existingIndex === -1) {
        allProducts.value.push(product)
      } else {
        allProducts.value[existingIndex] = product
      }
    })
  } catch (err) {
    error.value = '获取商品列表失败'
    console.error('获取商品列表失败:', err)
  } finally {
    loading.value = false
  }
}

// 搜索处理
const onSearch = () => {
  if (searchKeyword.value.trim()) {
    // 这里可以添加搜索逻辑
    console.log('搜索:', searchKeyword.value)
  }
}

// 获取店铺信息
const fetchShopInfo = async () => {
  try {
    const response = await getShopListAPI()
    if (response.data.length > 0) {
      shopInfo.value = response.data[0]
      // console.log(response.data);
      // console.log(shopInfo);
    }
  } catch (err) {
    console.error('获取店铺信息失败:', err)
  }
}

// 组件挂载时获取分类列表和店铺信息
onMounted(() => {
  fetchCategories()
  fetchShopInfo()
  fetchCartList()
  // 延迟打印，确保数据已经获取
  // setTimeout(() => {
  //   console.log('传递给CategoryPanel的shopInfo:', shopInfo.value)
  // }, 1000)
})
</script>

<template>
  <view class="container">
    <CategoryPanel :shop-info="shopInfo" />
    
    <!-- 主要内容区域 -->
    <view class="main-content">
      <!-- 左侧分类导航 -->
      <view class="category-nav">
        <view v-if="loading" class="loading">加载中...</view>
        <view v-else-if="error" class="error">{{ error }}</view>
        <view 
          v-for="category in categories" 
          :key="category.id"
          class="category-item"
          :class="{ active: selectedCategory === category.id }"
          @click="changeCategory(category.id)"
        >
          <text class="category-name">{{ category.name }}</text>
        </view>
      </view>
      
      <!-- 右侧商品区域 -->
      <view class="product-area">
        <!-- 搜索框 -->
        <view class="search-container">
          <view class="search-box" @click="onSearch">
            <uni-icons type="search" size="20" color="#999" class="search-icon"></uni-icons>
            <input
              type="text"
              placeholder="搜索"
              v-model="searchKeyword"
              @confirm="onSearch"
              class="search-input"
            />
          </view>
        </view>
        
        <!-- 商品列表 -->
        <view class="product-list">
          <view v-if="loading" class="loading">加载中...</view>
          <view v-else-if="error" class="error">{{ error }}</view>
          <view v-else-if="currentProducts.length === 0" class="empty">暂无商品</view>
          <view v-for="product in currentProducts" :key="product.id" class="product-item">
            <image :src="`${imageUrl}/mian.png`" class="product-image" @click="handleProductClick(product)"></image>
            <view class="product-info">
              <view class="product-name">{{ product.name }}</view>
              <view v-if="product.stock > 0" class="product-stock">还剩{{ product.stock }}份</view>
              <view v-else class="product-stock sold-out">已售罄</view>
              <view class="product-price">
                <text class="price">¥{{ product.price }}</text>
                <text v-if="product.originalPrice > product.price" class="original-price">¥{{ product.originalPrice }}</text>
                <text v-if="product.discount" class="discount">{{ product.discount }}</text>
              </view>
              <view class="add-btn" v-if="product.stock > 0" @click="handleAddToCart(product, $event)">
                <image src="/static/images/add.png" class="add-icon" mode="aspectFit"></image>
              </view>
            </view>
          </view>
        </view>
      </view>
    </view>
    
    <!-- 底部营业状态和购物车 -->
    <view class="bottom-bar">
      <view class="business-info">
        <uni-icons type="icon-coffee" size="20" color="#666"></uni-icons>
        <view class="hours-info">
          <view class="status">营业中</view>
          <view class="time">营业时间：{{ shopInfo.businessHours }}</view>
        </view>
      </view>
      <view class="cart-icon" @click="openCartPopup">
        <uni-icons type="cart" size="28" color="#fff"></uni-icons>
        <view v-if="cartItemCount > 0" class="cart-badge">{{ cartItemCount }}</view>
      </view>
    </view>
    
    <!-- 商品详情弹框 -->
    <view v-show="showProductPopup" class="product-popup-overlay" @click="closeProductPopup">
      <view :class="['product-popup', productPopupAnimation]" @click.stop>
        <view class="product-popup-header">
          <view class="close-button" @click="closeProductPopup">
            <uni-icons type="down" size="12" color="#666"></uni-icons>
          </view>
        </view>
        <view class="product-popup-content" v-if="selectedProduct">
          <view class="product-popup-image">
            <image :src="`${imageUrl}/mian.png`" mode="aspectFill"></image>
          </view>
          <view class="product-popup-info">
            <view class="product-popup-name">{{ selectedProduct.name }}</view>
            <view class="product-popup-price">
              <text class="price">¥{{ selectedProduct.price }}</text>
              <text v-if="selectedProduct.originalPrice > selectedProduct.price" class="original-price">¥{{ selectedProduct.originalPrice }}</text>
              <text v-if="selectedProduct.discount" class="discount">{{ selectedProduct.discount }}</text>
            </view>
            <view v-if="selectedProduct.stock > 0" class="product-popup-stock">还剩{{ selectedProduct.stock }}份</view>
            <view v-else class="product-popup-stock sold-out">已售罄</view>
            <view class="product-popup-share">
              <uni-icons type="weixin" size="20" color="green"></uni-icons>
              <text class="share-text">分享</text>
            </view>
          </view>
        </view>
        <view v-if="selectedProduct && selectedProduct.stock > 0" class="product-popup-footer">
          <view class="add-to-cart-btn" @click="handleAddToCartFromDetail">
            <uni-icons type="plus" size="24" color="#fff"></uni-icons>
            添加到购物车
          </view>
        </view>
      </view>
    </view>
    
    <!-- 购物车弹窗 -->
    <view v-show="showCartPopup" class="cart-popup-overlay" @click="closeCartPopup">
      <view :class="['cart-popup', cartPopupAnimation]" @click.stop>
        <view class="cart-header">
          <view class="cart-title">购物车</view>
          <view v-if="cartData.cartList.length > 0" class="clear-btn" @click="handleClearCart">
            <uni-icons type="trash" size="20" color="#666"></uni-icons>
            清空
          </view>
          <view class="close-cart" @click="closeCartPopup">
            <uni-icons type="close" size="24" color="#666"></uni-icons>
          </view>
        </view>
        
        <view class="cart-content">
          <view v-if="cartData.cartList.length === 0" class="empty-cart">
            <uni-icons type="cart" size="80" color="#ddd"></uni-icons>
            <view class="empty-text">购物车空空如也~</view>
          </view>
          
          <view v-else class="cart-list">
            <view v-for="item in processedCartList" :key="item.id" class="cart-item">
              <image :src="`${imageUrl}/mian.png`" class="cart-item-image"></image>
              <view class="cart-item-info">
                <view class="cart-item-name">{{ item.product?.name || '商品' }}</view>
                <view class="cart-item-price">¥{{ item.price }}</view>
              </view>
              <view class="cart-item-actions">
                <view class="quantity-btn" @click="handleUpdateQuantity(item, -1)">
                  <uni-icons type="minus" size="16" color="#666"></uni-icons>
                </view>
                <view class="quantity-num">{{ item.quantity }}</view>
                <view class="quantity-btn" @click="handleUpdateQuantity(item, 1)">
                  <uni-icons type="plus" size="16" color="#666"></uni-icons>
                </view>
                <view class="delete-btn" @click="handleDeleteItem(item)">
                  <uni-icons type="trash" size="18" color="#ff4d4f"></uni-icons>
                </view>
              </view>
            </view>
          </view>
        </view>
        
        <view v-if="cartData.cartList.length > 0" class="cart-footer">
          <view class="total-info">
            <text class="total-label">合计：</text>
            <text class="total-price">¥{{ cartData.totalAmount.toFixed(2) }}</text>
          </view>
          <view class="submit-btn" @click="handleCheckout">去结算</view>
        </view>
      </view>
    </view>
  </view>
</template>

<style lang="scss">
page {
  background-color: #f7f7f7;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.container {
  flex: 1;
  display: flex;
  flex-direction: column;
}

// 主要内容区域
.main-content {
  flex: 1;
  display: flex;
  overflow: hidden;
}

// 左侧分类导航
.category-nav {
  width: 200rpx;
  background-color: #f5f5f5;
  overflow-y: auto;
  
  .category-item {
    padding: 24rpx 0;
    text-align: center;
    font-size: 26rpx;
    color: #666;
    border-left: 4rpx solid transparent;
    
    &.active {
      background-color: #fff;
      color: #333;
      font-weight: 500;
      border-left-color: #673ab7;
    }
  }
}

// 右侧商品区域
.product-area {
  flex: 1;
  background-color: #fff;
  overflow-y: auto;
  padding-bottom: 100rpx;
}

// 搜索框样式
.search-container {
  padding: 20rpx;
  border-bottom: 1rpx solid #f0f0f0;
  
  .search-box {
    display: flex;
    align-items: center;
    background-color: #f5f5f5;
    border-radius: 24rpx;
    padding: 12rpx 20rpx;
    
    .search-icon {
      margin-right: 12rpx;
    }
    
    .search-input {
      flex: 1;
      font-size: 26rpx;
      color: #333;
      background: transparent;
      outline: none;
    }
  }
}

// 商品列表样式
.product-list {
  padding: 20rpx;
  
  .loading,
  .error,
  .empty {
    text-align: center;
    padding: 40rpx 0;
    color: #999;
    font-size: 24rpx;
  }
  
  .error {
    color: #ff4444;
  }
  
  .product-item {
    display: flex;
    padding: 20rpx;
    border-bottom: 1rpx solid #f0f0f0;
    position: relative;
    
    .product-image {
      width: 120rpx;
      height: 120rpx;
      border-radius: 8rpx;
      margin-right: 20rpx;
      object-fit: cover;
    }
    
    .product-info {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      
      .product-name {
        font-size: 28rpx;
        color: #333;
        margin-bottom: 8rpx;
      }
      
      .product-stock {
        font-size: 22rpx;
        color: #666;
        margin-bottom: 8rpx;
        
        &.sold-out {
          color: #999;
        }
      }
      
      .product-price {
        display: flex;
        align-items: center;
        gap: 12rpx;
        
        .price {
          font-size: 32rpx;
          font-weight: 600;
          color: #ff4444;
        }
        
        .original-price {
          font-size: 24rpx;
          color: #999;
          text-decoration: line-through;
        }
        
        .discount {
          font-size: 20rpx;
          color: #fff;
          background-color: #ff4444;
          padding: 2rpx 8rpx;
          border-radius: 12rpx;
        }
      }
    }
    
    .add-btn {
      position: absolute;
      bottom: 20rpx;
      right: 20rpx;
      width: 50rpx;
      height: 50rpx;
      border-radius: 20%;
      background-color: #ff4444;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    
    .add-icon {
      width: 40rpx;
      height: 40rpx;
    }
  }
}

// 底部营业状态和购物车样式
.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #fff;
  box-shadow: 0 -2rpx 8rpx rgba(0, 0, 0, 0.1);
  padding: 20rpx 30rpx;
  display: flex;
  align-items: center;
  justify-content: space-between;
  
  .business-info {
    display: flex;
    align-items: center;
    gap: 16rpx;
    
    .hours-info {
      .status {
        font-size: 26rpx;
        color: #27ba9b;
        font-weight: 500;
        margin-bottom: 4rpx;
      }
      
      .time {
        font-size: 24rpx;
        color: #666;
      }
    }
  }
  
  .cart-icon {
    width: 80rpx;
    height: 80rpx;
    background: linear-gradient(135deg, #27ba9b 0%, #1fa87f 100%);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    
    .cart-badge {
      position: absolute;
      top: -8rpx;
      right: -8rpx;
      min-width: 36rpx;
      height: 36rpx;
      background-color: #ff4d4f;
      color: #fff;
      font-size: 20rpx;
      border-radius: 18rpx;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 0 8rpx;
    }
  }
}

// 购物车弹窗样式
.cart-popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9998;
  display: flex;
  align-items: flex-end;
  animation: fadeIn 0.3s ease;
}

.cart-popup {
  background-color: white;
  border-radius: 20rpx 20rpx 0 0;
  width: 100%;
  max-height: 70vh;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  transform: translateY(100%);
}

.cart-popup.show {
  animation: slideUp 0.3s ease forwards;
}

.cart-popup.hide {
  animation: slideDown 0.3s ease forwards;
}

.cart-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 30rpx;
  border-bottom: 1rpx solid #f0f0f0;
  
  .cart-title {
    font-size: 32rpx;
    font-weight: 600;
    color: #333;
  }
  
  .clear-btn {
    display: flex;
    align-items: center;
    gap: 8rpx;
    font-size: 26rpx;
    color: #666;
  }
  
  .close-cart {
    padding: 10rpx;
  }
}

.cart-content {
  flex: 1;
  overflow-y: auto;
  padding: 20rpx 30rpx;
  
  .empty-cart {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 100rpx 0;
    
    .empty-text {
      font-size: 28rpx;
      color: #999;
      margin-top: 20rpx;
    }
  }
  
  .cart-list {
    .cart-item {
      display: flex;
      align-items: center;
      padding: 24rpx 0;
      border-bottom: 1rpx solid #f5f5f5;
      
      &:last-child {
        border-bottom: none;
      }
      
      .cart-item-image {
        width: 120rpx;
        height: 120rpx;
        border-radius: 12rpx;
        margin-right: 20rpx;
      }
      
      .cart-item-info {
        flex: 1;
        
        .cart-item-name {
          font-size: 28rpx;
          color: #333;
          margin-bottom: 12rpx;
        }
        
        .cart-item-price {
          font-size: 32rpx;
          font-weight: 600;
          color: #ff4d4f;
        }
      }
      
      .cart-item-actions {
        display: flex;
        align-items: center;
        gap: 20rpx;
        
        .quantity-btn {
          width: 48rpx;
          height: 48rpx;
          border: 1rpx solid #ddd;
          border-radius: 8rpx;
          display: flex;
          align-items: center;
          justify-content: center;
        }
        
        .quantity-num {
          min-width: 40rpx;
          text-align: center;
          font-size: 28rpx;
          color: #333;
        }
        
        .delete-btn {
          padding: 10rpx;
        }
      }
    }
  }
}

.cart-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20rpx 30rpx;
  border-top: 1rpx solid #f0f0f0;
  
  .total-info {
    .total-label {
      font-size: 28rpx;
      color: #666;
    }
    
    .total-price {
      font-size: 36rpx;
      font-weight: 600;
      color: #ff4d4f;
    }
  }
  
  .submit-btn {
    padding: 20rpx 60rpx;
    background: linear-gradient(135deg, #27ba9b 0%, #1fa87f 100%);
    color: #fff;
    font-size: 28rpx;
    border-radius: 40rpx;
  }
}

// 商品详情弹框样式
.product-popup-overlay {
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

.product-popup {
  background-color: white;
  border-radius: 20rpx 20rpx 0 0;
  width: 100%;
  max-height: 80vh;
  overflow: hidden;
  transform: translateY(100%);
}

.product-popup.show {
  animation: slideUp 0.3s ease forwards;
}

.product-popup.hide {
  animation: slideDown 0.3s ease forwards;
}

.product-popup-header {
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

.product-popup-content {
  padding: 30rpx;
  
  .product-popup-image {
    width: 100%;
    height: 400rpx;
    border-radius: 12rpx;
    overflow: hidden;
    margin-bottom: 20rpx;
    
    image {
      width: 100%;
      height: 100%;
    }
  }
  
  .product-popup-info {
    margin-bottom: 20rpx;
    position: relative;
    
    .product-popup-name {
      font-size: 28rpx;
      font-weight: 600;
      color: #333;
      margin-bottom: 16rpx;
      line-height: 1.4;
    }
    
    .product-popup-price {
      display: flex;
      align-items: center;
      gap: 12rpx;
      margin-bottom: 12rpx;
      
      .price {
        font-size: 32rpx;
        font-weight: 600;
        color: #ff4d4f;
      }
      
      .original-price {
        font-size: 20rpx;
        color: #999;
        text-decoration: line-through;
      }
      
      .discount {
        font-size: 18rpx;
        color: white;
        background-color: #ff4d4f;
        padding: 2rpx 8rpx;
        border-radius: 4rpx;
      }
    }
    
    .product-popup-share {
      position: absolute;
      top: 0;
      right: 0;
      display: flex;
      align-items: center;
      gap: 8rpx;
      font-size: 20rpx;
      color: #333;
      // background-color: #f5f5f5;
      padding: 0 ;
      border-radius: 20rpx;
    }
    
    .product-popup-stock {
      position: absolute;
      top: 57rpx;
      right: 0;
      font-size: 20rpx;
      color: #666;
      
      &.sold-out {
        color: #999;
      }
    }
  }
  
  .shop-status {
    text-align: center;
    padding: 16rpx;
    background-color: #f5f5f5;
    border-radius: 8rpx;
    font-size: 20rpx;
    color: #666;
  }
}

.product-popup-footer {
  padding: 20rpx 30rpx 30rpx;
  border-top: 1rpx solid #f0f0f0;
  
  .add-to-cart-btn {
    width: 100%;
    padding: 24rpx;
    background: linear-gradient(135deg, #ff6b6b 0%, #ff4d4f 100%);
    color: #fff;
    font-size: 30rpx;
    font-weight: 500;
    border-radius: 40rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 12rpx;
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

@keyframes slideDown {
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(100%);
  }
}
</style>

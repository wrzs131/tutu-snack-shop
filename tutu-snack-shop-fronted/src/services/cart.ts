import type { CartData, AddCartParams, UpdateCartParams } from '@/types/cart.d'
import { http } from '@/utils/http'

// 获取购物车列表
export const getCartListAPI = () => {
  return http<CartData>({
    method: 'GET',
    url: '/cart/list'
  })
}

// 添加商品到购物车
export const addToCartAPI = (data: AddCartParams) => {
  return http<any>({
    method: 'POST',
    url: '/cart/add',
    data
  })
}

// 更新购物车商品
export const updateCartAPI = (data: UpdateCartParams) => {
  return http<any>({
    method: 'PUT',
    url: '/cart/update',
    data
  })
}

// 删除购物车商品
export const deleteFromCartAPI = (id: number) => {
  return http<any>({
    method: 'DELETE',
    url: `/cart/delete/${id}`
  })
}

// 清空购物车
export const clearCartAPI = () => {
  return http<any>({
    method: 'DELETE',
    url: '/cart/clear'
  })
}

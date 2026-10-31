import type { OrderListResponse, OrderItemListResponse, OrderQueryParam, CreateOrderParam, OrderItem } from "@/types/order.d"
import { http } from "@/utils/http"

/**
 * 获取订单列表
 * @param params 订单查询参数
 */
export const getOrderListAPI = (params: OrderQueryParam) => {
  return http<OrderListResponse>({
    method: 'GET',
    url: '/order/list',
    data: params
  })
}

/**
 * 根据订单ID获取订单详情（含后台管理端流转后的真实状态）
 * @param orderId 订单ID
 */
export const getOrderByIdAPI = (orderId: number) => {
  return http<OrderItem>({
    method: 'GET',
    url: `/order/getById/${orderId}`
  })
}

/**
 * 根据订单ID获取订单项列表
 * @param orderId 订单ID
 */
export const getOrderItemsByOrderIdAPI = (orderId: number) => {
  return http<OrderItemListResponse>({
    method: 'GET',
    url: `/orderItem/listByOrderId/${orderId}`
  })
}

/**
 * 创建订单
 * @param params 创建订单参数
 */
export const createOrderAPI = (params: CreateOrderParam) => {
  return http<any>({
    method: 'POST',
    url: '/order/create',
    data: params
  })
}

/**
 * 更新订单状态
 * @param orderId 订单ID
 * @param status 新状态
 */
export const updateOrderStatusAPI = (orderId: number, status: string) => {
  return http<any>({
    method: 'PUT',
    url: `/order/updateStatus?id=${orderId}&status=${encodeURIComponent(status)}`
  })
}
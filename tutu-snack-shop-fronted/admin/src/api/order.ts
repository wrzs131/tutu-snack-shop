import request from '@/utils/request'

export interface OrderItem {
  id: number
  orderId: number
  productId: number
  quantity: number
  price: number
  product?: {
    id: number
    name: string
    price: number
    image: string
  }
}

export interface Order {
  id: number
  userId: number
  totalAmount: number
  status: string
  createdAt: string
  updatedAt: string
}

export interface OrderPage {
  total: number
  rows: Order[]
}

// 订单状态常量（与后端状态机保持一致）
export const ORDER_STATUS = {
  WAIT_CONFIRM: '待确认',
  ACCEPTED: '已接单',
  PICKED: '已取餐',
  DELIVERING: '配送中',
  DELIVERED: '已送达',
  REFUNDED: '已退款'
} as const

/** 分页查询订单（支持状态过滤） */
export const listOrders = (params: {
  page: number
  pageSize: number
  status?: string
}) => request.get('/order/list', { params }) as unknown as Promise<OrderPage>

/** 根据订单ID查询订单项 */
export const listOrderItems = (orderId: number) =>
  request.get(`/orderItem/listByOrderId/${orderId}`) as unknown as Promise<OrderItem[]>

/** 后台订单状态流转 */
export const flowOrderStatus = (id: number, status: string, role: string) =>
  request.put('/order/admin/flowStatus', null, {
    params: { id, status, role }
  }) as unknown as Promise<void>

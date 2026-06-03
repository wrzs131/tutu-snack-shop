// 购物车商品项类型
export type CartItem = {
  id: number
  productId: number
  quantity: number
  price: number
  product?: {
    id: number
    name: string
    price: number
    originalPrice: number
    stock: number
    image: string
    categoryId: number
    discount: string
    createdAt: string
    updatedAt: string
  }
}

// 购物车数据类型
export type CartData = {
  cartList: CartItem[]
  totalAmount: number
}

// 添加购物车请求参数
export type AddCartParams = {
  productId: number
  quantity: number
  price: number
}

// 更新购物车请求参数
export type UpdateCartParams = {
  id: number
  productId: number
  quantity: number
  price: number
}

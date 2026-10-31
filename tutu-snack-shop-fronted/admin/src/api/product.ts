import request from '@/utils/request'

export interface Product {
  id?: number
  name: string
  price: number
  originalPrice?: number
  stock: number
  image: string
  categoryId: number
  discount?: string
}

/** 查询商品列表 */
export const listProducts = () =>
  request.get('/product/list') as unknown as Promise<Product[]>

/** 新增商品 */
export const addProduct = (data: Product) =>
  request.post('/product/insert', data) as unknown as Promise<void>

/** 更新商品 */
export const updateProduct = (data: Product) =>
  request.put('/product/update', data) as unknown as Promise<void>

/** 删除商品 */
export const deleteProduct = (id: number) =>
  request.delete(`/product/delete/${id}`) as unknown as Promise<void>

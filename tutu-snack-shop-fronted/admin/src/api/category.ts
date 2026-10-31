import request from '@/utils/request'

export interface Category {
  id?: number
  name: string
}

/** 查询分类列表 */
export const listCategories = () =>
  request.get('/category/list') as unknown as Promise<Category[]>

/** 新增分类 */
export const addCategory = (data: Category) =>
  request.post('/category/insert', data) as unknown as Promise<void>

/** 更新分类 */
export const updateCategory = (data: Category) =>
  request.put('/category/update', data) as unknown as Promise<void>

/** 删除分类 */
export const deleteCategory = (id: number) =>
  request.delete(`/category/delete/${id}`) as unknown as Promise<void>

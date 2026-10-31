import request from '@/utils/request'

export interface User {
  id?: number
  nickname: string
  phone?: string
  avatar?: string
  role: string
  username?: string
  password?: string
  status?: string
  createdAt?: string
}

/** 查询用户列表 */
export const listUsers = () =>
  request.get('/user/list') as unknown as Promise<User[]>

/** 新增用户（含员工账号） */
export const addUser = (data: User) =>
  request.post('/user/insert', data) as unknown as Promise<void>

/** 更新用户 */
export const updateUser = (data: User) =>
  request.put('/user/update', data) as unknown as Promise<void>

/** 删除用户 */
export const deleteUser = (id: number) =>
  request.delete(`/user/delete/${id}`) as unknown as Promise<void>

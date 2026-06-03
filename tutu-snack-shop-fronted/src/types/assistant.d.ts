/** 聊天消息角色类型 */
export type ChatMessageRole = 'user' | 'assistant'

/** 单条聊天消息类型 */
export type ChatMessage = {
  /** 角色 */
  role: ChatMessageRole
  /** 消息内容 */
  content: string
}

/** 聊天请求参数类型 */
export type ChatRequest = {
  /** 消息列表 */
  messages: ChatMessage[]
}

/** 聊天响应数据类型 */
export type ChatResponse = {
  /** 回复内容 */
  content: string
}

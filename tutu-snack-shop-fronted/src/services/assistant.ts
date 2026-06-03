import type { ChatRequest, ChatResponse } from '@/types/assistant.d'
import { http } from '@/utils/http'

/**
 * 发送聊天消息
 * @param data 聊天请求参数
 */
export const chatAPI = (data: ChatRequest) => {
  return http<ChatResponse>({
    method: 'POST',
    url: '/qianfan/chat',
    data
  })
}

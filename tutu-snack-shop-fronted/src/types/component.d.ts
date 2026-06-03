/**
 * declare module '@vue/runtime-core'
 *   现调整为
 * declare module 'vue'
 */
import 'vue'
import XtxSwiper from "@/components/XtxSwiper.vue"
declare module 'vue' {
  export interface GlobalComponents {
    XtxSwiper: typeof XtxSwiper
  }
}

// import XtxSwiper from "@/components/XtxSwiper.vue"
// declare module '@vue/runtime-core' {
//   export interface GlobalComponents {
//     //通过typeof关键词获取组件的实例类型
//     XtxSwiper: typeof XtxSwiper
//   }
// }

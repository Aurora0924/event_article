import { defineStore } from "pinia"
import { ref } from 'vue'

/*
    defineStore参数描述：
        第一个参数：给状态起名，具有唯一性
        第二个参数：函数，可以把定义该状态中拥有的内容

    defineStore返回值描述：
        返回的是一个函数，将来可以调用该函数，得到第二个参数中返回的内容
*/
export const useUserInfo = defineStore('userInfo', () => {
    const info = ref({})

    const setInfo = (newInfo) => {
        info.value = newInfo
    }

    const removeInfo = () => {
        info.value = {}
    }
    return {
        info, setInfo, removeInfo
    }

},
    //参数持久化
    {
        persist: true
    }
)
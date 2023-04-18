import {defineStore} from "pinia";

interface ThemeState {
  backgroundColor: string
}
export const useThemeStore = defineStore({
  id: 'app-theme',
  state: ():ThemeState => ({
    backgroundColor: '',
  }),
  actions: {
    setThemeColor(color:string){
      this.backgroundColor = color
    }
  },
  persist:true,
})
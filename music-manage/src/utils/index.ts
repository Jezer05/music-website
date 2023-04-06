// 获得图片
export function attachImageUrl(url:string) {
  // 这里配置proxy后很神奇
  return `http:/api/${url}`
}
// 解析日期
export function getBirth(cellValue: any) {
  if (cellValue == null || cellValue == "") return "";
  const date = new Date(cellValue);
  const year = date.getFullYear();
  const month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
  const day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
  return year + "-" + month + "-" + day;
}
// 性别映射
export function sexMap(value : number){
  if (value === 0) {
    return "女";
  } else if (value === 1) {
    return "男";
  } else if (value === 2) {
    return "组合";
  } else if (value === 3) {
    return "不明";
  }
}
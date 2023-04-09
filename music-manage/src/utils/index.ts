// 获得图片
export function attachImageUrl(url:string) {
  // 这里配置proxy后很神奇
  return `http:/api/${url}`
}
// 解析日期
export function parseBirth(cellValue: any) {
  if (cellValue == null || cellValue == "") return "";
  const date = new Date(cellValue);
  const year = date.getFullYear();
  const month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
  const day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
  return year + "-" + month + "-" + day;
}
// 性别映射
export function parseSex(value : number){
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
// 解析歌词
export function parseLyric(text:string) {
  const lines = text.split("\n");
  const pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
  const result = [];

  // 对于歌词格式不对的特殊处理
  if (!/\[.+\]/.test(text)) {
    return [text];
  }
  for (const item of lines) {
    if (pattern.test(item)) {
      const value = item.replace(pattern, ""); // 存歌词
      result.push(value);
    }
  }
  return result;
}
export function attachUrl(url:string) {
  // 这里因配置proxy，所以不用也不能加前缀
  return `http:/api/${url}`
}

export function formatSeconds(value:number) {
  value = Math.floor(value);
  let minute = 0;
  let second = value;
  if (value > 60) {
    minute = Math.floor(value / 60);
    second = value % 60;
  }
  let minute_str = "";
  let second_str = "";
  if (minute < 10)
    minute_str = "0" + minute;
  else minute_str = "" + minute;
  if (second < 10)
    second_str = "0" + second;
  else second_str = "" + second
  return minute_str + ":" + second_str;
}
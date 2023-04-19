export interface LoginReqForm {
  username: string;
  password: string;
}

export interface MusicEntity{
  id:number,
  name:string,
  pic:string,
  url:string,
  lyric?:string
}
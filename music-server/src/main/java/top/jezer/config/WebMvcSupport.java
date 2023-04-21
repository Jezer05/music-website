package top.jezer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import top.jezer.constant.ResourceLocation;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcSupport extends WebMvcConfigurationSupport {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    /**
     * 处理乱码
     */
    public HttpMessageConverter<String> responseBodyConverter() {
        final StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converter.setWriteAcceptCharset(false);
        return converter;
    }
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (converters.size() > 0) {
            converters.add(converters.get(0));
            converters.set(0, responseBodyConverter());
        } else {
            converters.add(responseBodyConverter());
        }
    }

    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
    }
    /**
     * token 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
        excludePath.add("/admins/login");  //登录
        excludePath.add("/consumers/login");  //登录
        excludePath.add("/consumers/register");  //注册
        excludePath.add("/songs");  //查询全部歌曲
        excludePath.add("/songLists/**");  //获得全部歌单
        excludePath.add("/comments/songList/detail/**");  //歌单评论
        excludePath.add("/listSongs/**");  //歌单歌曲

        ////静态资源
        excludePath.add("/img/**");
        excludePath.add("/song/**");
        excludePath.add("/error");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }
    /**
     * 静态资源访问不走springMvc
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/avatarImages/**")
                .addResourceLocations(ResourceLocation.AVATAR_IMAGES_PATH);
        registry.addResourceHandler("/img/songListPic/**")
                .addResourceLocations(ResourceLocation.SONGLIST_PIC_PATH);
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations(ResourceLocation.SONG_PIC_PATH);
        registry.addResourceHandler("/song/**")
                .addResourceLocations(ResourceLocation.SONG_PATH);
        registry.addResourceHandler("/img/singerPic/**")
                .addResourceLocations(ResourceLocation.SINGER_PIC_PATH);
        registry.addResourceHandler("/img/theme/**")
                .addResourceLocations(ResourceLocation.THEME_PIC_PATH);
    }
}

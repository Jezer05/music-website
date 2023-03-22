package top.jezer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import top.jezer.constant.ResourceLocation;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class WebMvcSupport extends WebMvcConfigurationSupport {
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
     * 静态资源访问不走springMvc
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/avatorImages/**")
                .addResourceLocations(ResourceLocation.AVATOR_IMAGES_PATH);
        registry.addResourceHandler("/img/songListPic/**")
                .addResourceLocations(ResourceLocation.SONGLIST_PIC_PATH);
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations(ResourceLocation.SONG_PIC_PATH);
        registry.addResourceHandler("/song/**")
                .addResourceLocations(ResourceLocation.SONG_PATH);
        registry.addResourceHandler("/img/singerPic/**")
                .addResourceLocations(ResourceLocation.SINGER_PIC_PATH);
    }
}

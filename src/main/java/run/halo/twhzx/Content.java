package run.halo.twhzx;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import run.halo.app.extension.AbstractExtension;
import run.halo.app.extension.GVK;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@GVK(group = "twhzx.halo.run",
    version = "v1alpha1",
    kind = "Content",
    plural = "content",
    singular = "content")
public class Content extends AbstractExtension {

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 50)
    private String title;

    @Schema(description = "关键字", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 150)
    private String keywords;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private ContentType type;

    @Schema(description = "链接", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String url;

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String content;

    @Schema(description = "文章", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String article;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "0")
    private Integer sort;

    public enum ContentType {
        Content(0, "内容"),
        Article(1, "文章"),
        Link(2, "链接");
        private int code;
        private String type;

        ContentType(int code, String type) {
            this.code = code;
            this.type = type;
        }
    }
}
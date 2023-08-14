package run.halo.twhzx;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import run.halo.app.extension.ListResult;
import run.halo.app.plugin.ApiVersion;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApiVersion("v1alpha1")
@RequestMapping("/content")
@RestController
@Slf4j
public class ContentController {

    // private final ReactiveExtensionClient client;
    //
    // public ContentController(ReactiveExtensionClient client) {
    //     System.out.println(client);
    //     this.client = client;
    // }

    @Autowired
    private ContentServices contentServices;

    @PostMapping("/test")
    public void starting(@RequestBody Content content) {
        log.info(content.getTitle());
    }

    @GetMapping("/list")
    public Mono<ListResult<Content>> getPerson(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        // log.debug();
        log.info("name: {}", name);
        Predicate<Content> predicate = new Predicate<Content>() {
            @Override
            public boolean test(Content s) {
                if (StringUtils.isBlank(name)) {
                    return true;
                }
                Pattern pattern = Pattern.compile(name);
                Matcher matcher = pattern.matcher(s.getTitle());
                Matcher matcher1 = pattern.matcher(s.getKeywords());
                return matcher.find() | matcher1.find();
            }
        };
        Comparator<Content> sortComp =
            (s1, s2) -> s2.getSort() - s1.getSort();

        return contentServices.list(Content.class, predicate, sortComp, page, size);
    }
}
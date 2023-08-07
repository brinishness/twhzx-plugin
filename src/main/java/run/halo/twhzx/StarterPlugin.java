package run.halo.twhzx;

import org.pf4j.PluginWrapper;
import org.springframework.stereotype.Component;
import run.halo.app.extension.Scheme;
import run.halo.app.extension.SchemeManager;
import run.halo.app.plugin.BasePlugin;

/**
 * <p>Plugin main class to manage the lifecycle of the plugin.</p>
 * <p>This class must be public and have a public constructor.</p>
 * <p>Only one main class extending {@link BasePlugin} is allowed per plugin.</p>
 *
 * @author guqing
 * @since 1.0.0
 */
@Component
public class StarterPlugin extends BasePlugin {
    private final SchemeManager schemeManager;

    public StarterPlugin(PluginWrapper wrapper, SchemeManager schemeManager) {
        super(wrapper);
        this.schemeManager = schemeManager;
    }

    @Override
    public void start() {
        schemeManager.register(Content.class);
        System.out.println("内容插件启动！");
    }

    @Override
    public void stop() {
        Scheme contentScheme = schemeManager.get(Content.class);
        schemeManager.unregister(contentScheme);
        System.out.println("内容插件停止！");
    }
}

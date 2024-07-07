package top.aenlly.qqrobot.core.common;

import cn.hutool.cache.impl.LRUCache;
import lombok.Data;
import org.springframework.stereotype.Component;
import top.aenlly.qqrobot.adapter.Command;
import top.aenlly.qqrobot.filter.AbstractFilterChain;
import top.aenlly.qqrobot.filter.MessageFilterChain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
public class GeneralContext {

    private final Map<String, Command> commandMap;

    private MessageFilterChain filterChain;

    public static LRUCache<String,CommandModeContext> commandStatusMap=new LRUCache<>(8);

    public GeneralContext(List<Command> commands, List<AbstractFilterChain> filterChains) {
        this.commandMap = commands.stream().collect(Collectors.toMap(Command::getName, c->c));;
        filterChains.sort(Comparator.comparing(MessageFilterChain::getOrder));
        for (int i = 0; i < filterChains.size()-1; ) {
            filterChains.get(i).nextFilterChain(filterChains.get(++i));
        }
        this.filterChain = filterChains.get(0);
    }
}

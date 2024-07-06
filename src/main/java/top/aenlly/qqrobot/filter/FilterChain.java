package top.aenlly.qqrobot.filter;

public class FilterChain {

    protected MessageFilterChain filterChain;

    public void nextFilterChain(MessageFilterChain chain){
        this.filterChain=chain;
    }
}

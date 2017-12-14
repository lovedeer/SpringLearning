package com.smart.util;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngine;

public class Json {

    public static void main(String[] args) throws Exception{
        ScriptEngine engine = new NashornScriptEngineFactory().getScriptEngine("nashorn");
        Object a = engine.eval("var a = {\"q\":'1',\"w\":'2'};a['q']");
        System.out.println(a);
    }
}

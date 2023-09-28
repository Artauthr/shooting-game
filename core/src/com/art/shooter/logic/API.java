package com.art.shooter.logic;

import com.art.shooter.entities.EntitySystem;
import com.art.shooter.ui.GameUI;
import com.art.shooter.utils.screenUtils.DebugLineRenderer;
import com.art.shooter.utils.screenUtils.Grid;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class API {

    private static API api;

    private ObjectMap<Class, Object> classMap;

    API () {
        classMap = new ObjectMap<>();
    }

    public static API getInstance () {
        if (api == null) {
            api = new API();
        }
        return api;
    }

    public void init () {
        //add API-s here
        register(CharacterManager.class);
        register(EntitySystem.class);
        register(Grid.class);
        register(GameLogic.class);
        register(DebugLineRenderer.class);
    }

    public static <T> T get (Class<T> clazz) {
        if (api.classMap.containsKey(clazz)) {
            return (T) api.classMap.get(clazz);
        }
        throw new RuntimeException(clazz.getSimpleName() + " not present in API");
    }


    public <T> T register (Class key, T object) {
        if (!classMap.containsKey(key)) {
            classMap.put(key, object);
        }
        return object;
    }

    public <T> T register (Class clazz) {
        if (!classMap.containsKey(clazz)) {
            try {
                Object object = ClassReflection.newInstance(clazz);
                classMap.put(clazz, object);
                return (T) object;
            } catch (ReflectionException e) {
                throw new RuntimeException(clazz.getSimpleName() + " failed to create");
            }
        } else {
            return (T) classMap.get(clazz);
        }
    }

    public static <T> T register (T object) {
        return api.register(object.getClass(), object);
    }

    public static void dispose () {
        ObjectMap<Class, Object> map = api.classMap;
        for (ObjectMap.Entry<Class, Object> entry : map) {
            if (entry.value instanceof Disposable) {
                ((Disposable) entry.value).dispose();
            }
        }
        map.clear();
    }


}

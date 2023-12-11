package com.natamus.playertrackingcompass.services;

import com.natamus.playertrackingcompass.services.helpers.PacketToClientHelper;
import com.natamus.playertrackingcompass.services.helpers.PacketToServerHelper;
import com.natamus.playertrackingcompass.util.Reference;

import java.util.ServiceLoader;

public class Services {
    public static final PacketToClientHelper PACKETTOCLIENT = load(PacketToClientHelper.class);
    public static final PacketToServerHelper PACKETTOSERVER = load(PacketToServerHelper.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("[" + Reference.NAME + "] Failed to load service for " + clazz.getName() + "."));
    }
}
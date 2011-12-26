/*
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2007-2008 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 */

package com.sun.grizzly.http.utils;

import com.sun.grizzly.Controller;
import com.sun.grizzly.ControllerStateListenerAdapter;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import com.sun.grizzly.http.SelectorThread;
import com.sun.grizzly.util.WorkerThreadImpl;
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author Alexey Stashok
 */
public class SelectorThreadUtils {

    /**
     *  Start controller in seperate thread
     */
    public static void startSelectorThread(final SelectorThread selectorThread) 
            throws IOException {
        
        selectorThread.setDisplayConfiguration(true);
        try {
            selectorThread.initEndpoint();
        } catch(InstantiationException e){
            throw new IOException(e.getMessage());
        }
        
        final CountDownLatch latch = new CountDownLatch(1);
        Controller controller = selectorThread.getController();
        controller.addStateListener(new ControllerStateListenerAdapter() {
            @Override
            public void onReady() {
                latch.countDown();
            }

            @Override
            public void onException(Throwable e) {
                SelectorThread.logger().log(Level.SEVERE, "Exception during " +
                        "starting the SelectorThread", e);
                latch.countDown();
            }
        });

        new WorkerThreadImpl(new Runnable() {
            public void run() {
                try {
                    selectorThread.startEndpoint();
                } catch (Exception ex) {
                }
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException ex) {
        }
        
        if (!controller.isStarted()) {
            throw new IllegalStateException("SelectorThread is not started!");
        }
    }

    /**
     *  Stop controller in seperate thread
     */
    public static void stopSelectorThread(SelectorThread selectorThread) {
        selectorThread.stopEndpoint();
    }

    public static void startControllers(SelectorThread[] selectorThreads) throws Exception {
        startSelectorThreads(Arrays.asList(selectorThreads));
    }

    public static void startSelectorThreads(Collection<SelectorThread> selectorThreads) throws Exception {
        for (SelectorThread selectorThread : selectorThreads) {
            startSelectorThread(selectorThread);
        }
    }

    public static void stopControllers(SelectorThread[] selectorThreads) {
        stopSelectorThreads(Arrays.asList(selectorThreads));
    }

    public static void stopSelectorThreads(Collection<SelectorThread> selectorThreads) {
        for (SelectorThread selectorThread : selectorThreads) {
            stopSelectorThread(selectorThread);
        }
    }
}
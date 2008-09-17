package org.apache.maven.plugin.coreit;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.Properties;
import java.io.FileOutputStream;
import java.io.File;

/**
 * Interpolate some envars that are embedded in the POM and make sure they pass through
 * the system.
 * 
 * @goal generate-envar-properties
 */
public class InterpolatedEnvarConfigurationMojo
    extends AbstractMojo
{
    /**
     * @parameter expression="${basedir}"
     */
    private String basedir;

    /**
     * @parameter expression="${mavenTestEnvar}"
     */
    private String mavenTestEnvar;

    public void execute()
        throws MojoExecutionException
    {
        if ( mavenTestEnvar == null )
        {
            throw new MojoExecutionException( "The mavenTestEnvar field should not be null! You must run this using the maven-core-it scripts! ");
        }
        
        try
        {
            Properties mojoGeneratedPropeties = new Properties();

            mojoGeneratedPropeties.put( "maven.test.envar", mavenTestEnvar );

            FileOutputStream fos = new FileOutputStream( new File( basedir, "target/mojo-generated.properties" ) );

            mojoGeneratedPropeties.store( fos, "# Properties generated by the execution of a mojo that uses interpolated envar values." );
        }
        catch( Exception e )
        {
            getLog().error( "Error creating mojo generated properties.", e );
        }
    }
}

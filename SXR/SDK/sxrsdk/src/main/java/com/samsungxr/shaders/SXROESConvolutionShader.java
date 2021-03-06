/* Copyright 2015 Samsung Electronics Co., LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.samsungxr.shaders;

import android.content.Context;

import com.samsungxr.SXRContext;
import com.samsungxr.SXRShaderData;
import com.samsungxr.SXRShaderTemplate;
import com.samsungxr.R;
import com.samsungxr.utility.TextFile;

/**
 * Shader which samples from an external texture.
 * This shader does not use light sources.
 * Fix anti-aliasing for eos texture
 * @<code>
 *    a_position    position vertex attribute
 *    a_texcoord    texture coordinate vertex attribute
 *    u_color       color to modulate texture
 *    u_opacity     opacity
 *    u_texture     external texture
 *    texelWidth    1.0f / image with
 *    texelHeight   1.0f / image height
 * </code>
 */
public class SXROESConvolutionShader extends SXRShaderTemplate
{
    public SXROESConvolutionShader(SXRContext gvrContext)
    {
        super("float3 u_color float u_opacity"
                + " float texelWidth float texelHeight",
              "samplerExternalOES u_texture",
              "float3 a_position float2 a_texcoord", GLSLESVersion.VULKAN);
        Context context = gvrContext.getContext();
        setSegment("FragmentTemplate", TextFile.readTextFile(context, R.raw.oes_convolution_frag));
        setSegment("VertexTemplate", TextFile.readTextFile(context, R.raw.oes_convolution_vert));
    }

    protected void setMaterialDefaults(SXRShaderData material)
    {
        material.setVec3("u_color", 1.0f, 1.0f, 1.0f);
        material.setFloat("u_opacity", 1.0f);

        material.setFloat("texelWidth", 1.0f / 1024.0f);
        material.setFloat("texelHeight", 1.0f / 1024.0f);
    }
}

#version 120

uniform vec2 InSize;
uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

void main() {
    vec3 color = texture2D(DiffuseSampler, texCoord).rgb;
    vec3 temp_color = texture2D(DiffuseSampler, texCoord).rgb;

    color.r = (temp_color.r * 0.3 + (temp_color.g * 0.3 + temp_color.b * 0.3)) / 0.9;
    color.g = (temp_color.g * 0.6 + (temp_color.r * 0.6 + temp_color.b * 0.6)) / 1.8;
    color.b = (temp_color.b * 0.1 + (temp_color.r * 0.1 + temp_color.g * 0.1)) / 0.3;

    gl_FragColor = vec4(color.rgb, 1.0f);
}
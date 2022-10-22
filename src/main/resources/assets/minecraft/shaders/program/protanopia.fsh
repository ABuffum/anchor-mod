#version 120

uniform vec2 InSize;
uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

void main() {
    vec3 color = texture2D(DiffuseSampler, texCoord).rgb;
    vec3 temp_color = texture2D(DiffuseSampler, texCoord).rgb;

    color.r = (temp_color.r * 0.55 + (temp_color.g * 0.5 + temp_color.b * 0.1)) / 1.15;
    color.g = (temp_color.g * 0.85 + (temp_color.r * 0.95)) / 1.8;
    color.b = (temp_color.b * 0.6 + (temp_color.g * 0.3)) / 0.9;

    gl_FragColor = vec4(color.rgb, 1.0f);
}
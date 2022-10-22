#version 120

uniform vec2 InSize;
uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

void main() {
    vec3 color = texture2D(DiffuseSampler, texCoord).rgb;
    vec3 temp_color = texture2D(DiffuseSampler, texCoord).rgb;

    color.r = (temp_color.r + (temp_color.g * 0.1)) / 1.1;
    color.g = (temp_color.g * 0.9 + (temp_color.r * 0.45 + temp_color.b)) /  2.35 ;
    color.b = (temp_color.b * 0.35 + (temp_color.g * 0.35)) / 0.7;

    gl_FragColor = vec4(color.rgb, 1.0f);
}
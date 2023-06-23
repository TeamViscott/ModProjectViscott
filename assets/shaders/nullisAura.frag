#define HIGHP

#define Inner vec4(0.0, 0.0, 0.0,100.0) / 255.0
#define Border vec4(255.0, 255.0, 255.0,255.0) / 255.0

#define step 2.0

uniform sampler2D u_texture;
uniform vec2 u_texsize;
uniform vec2 u_invsize;
uniform float u_time;
uniform vec2 u_offset;

varying vec2 v_texCoords;

void main() {
    vec2 coords = (v_texCoords.xy * u_texsize) + u_offset;

    vec4 color = texture2D(u_texture, v_texCoords.xy);
    vec2 v = u_invsize;
    float wave = sin((coords.y + u_time)/6.0);

    vec4 maxed = max(max(max(texture2D(u_texture, v_texCoords.xy + vec2(wave, step) * v), texture2D(u_texture, v_texCoords.xy + vec2(wave, -step) * v)), texture2D(u_texture, v_texCoords.xy + vec2(step + wave, 0.0) * v)), texture2D(u_texture, v_texCoords.xy + vec2(-step + wave, 0.0) * v));

    if (texture2D(u_texture, v_texCoords.xy + vec2(wave,0)*v).a < 0.9 && maxed.a > 0.9) {
        gl_FragColor = Border;
    } else {
        if (color.a > 0.0) {
            color = Inner;
            color.a = sin((coords.x + coords.y + u_time)/15.0) * 0.1 + 0.2;
            /*
            if (mod(coords.x+ coords.y + (u_time / 4.0), 5.0) < 1.0) {
                color = Inner.a = coords.x;
            } else {
                color = Inner.a = 100.0/255.0;
            }
            */
        }

        gl_FragColor = color;
    }
}
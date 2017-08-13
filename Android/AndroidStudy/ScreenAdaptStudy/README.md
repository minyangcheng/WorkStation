#### 图标icon尺寸
密度	建议尺寸
mipmap-mdpi	48 * 48
mipmap-hdpi	72 * 72
mipmap-xhdpi	96 * 96
mipmap-xxhdpi	144 * 144
mipmap-xxxhdpi	192 * 192

#### dpi
dpi范围	密度
0dpi ~ 120dpi	ldpi
120dpi ~ 160dpi	mdpi
160dpi ~ 240dpi	hdpi
240dpi ~ 320dpi	xhdpi
320dpi ~ 480dpi	xxhdpi
480dpi ~ 640dpi	xxxhdpi

####图片需找规则
1. 首先假设当前手机为xxhdpi

2. 当我引用android_logo这张图时，如果drawable-xxhdpi文件夹下有这张图就会优先被使用，在这种情况下，图片是不会被缩放的。但是，如果drawable-xxhdpi文件夹下没有这张图时， 系统就会自动去其它文件夹下找这张图了，优先会去更高密度的文件夹下找这张图片，我们当前的场景就是drawable-xxxhdpi文件夹，然后发现这里也没有android_logo这张图，接下来会尝试再找更高密度的文件夹，发现没有更高密度的了，这个时候会去drawable-nodpi文件夹找这张图，发现也没有，那么就会去更低密度的文件夹下面找，依次是drawable-xhdpi -> drawable-hdpi -> drawable-mdpi -> drawable-ldpi。 

3. 那么比如说现在终于在drawable-mdpi文件夹下面找到android_logo这张图了，但是系统会认为你这张图是专门为低密度的设备所设计的，如果直接将这张图在当前的高密度设备上使用就有可能会出现像素过低的情况，于是系统自动帮我们做了这样一个放大操作。 
那么同样的道理，如果系统是在drawable-xxxhdpi文件夹下面找到这张图的话，它会认为这张图是为更高密度的设备所设计的，如果直接将这张图在当前设备上使用就有可能会出现像素过高的情况，于是会自动帮我们做一个缩小的操作。

> 每一种密度的dpi范围都有一个最大值，这个最大值之间的比例就是图片会被系统自动放大的比例。 
package dev.utils.app.assist.exif;

import androidx.exifinterface.media.ExifInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * detail: 图片 EXIF Tag Group 常量类
 * @author Ttt
 * <pre>
 *     copy {@link androidx.exifinterface.media.ExifInterface}
 *     static final ExifTag[][] EXIF_TAGS = new ExifTag[][]
 *     修改为不可变 List 存储, 如后续库迭代导致 TAG 常量更新不及时
 *     也可自行执行 new ArrayList<>(list) 再进行 add 使用
 *     并提 issue 通知更新即可 ( 不定时进行更新全部库 )
 *     <p></p>
 *     有需要设置单独的 TAG 直接使用 ExifInterface.TAG_XXX 即可
 *     <p></p>
 *     快捷替换正则
 *     , [0-9]+[, \w]+[, \w]+\),
 * </pre>
 */
public final class ExifTag {

    private ExifTag() {
    }

    /**
     * 快捷创建 List 简化 add 操作
     * @param tags TAG 可变数组
     * @return List
     */
    public static List<String> asList(final String... tags) {
        List<String> list = new ArrayList<>();
        if (tags != null) Collections.addAll(list, tags);
        return Collections.unmodifiableList(list);
    }

    // List of Exif tag groups
    public static final List<List<String>> EXIF_TAGS;

    // Primary image IFD TIFF tags (See JEITA CP-3451C Section 4.6.8 Tag Support Levels)
    public static final List<String> IFD_TIFF_TAGS;

    // Primary image IFD Exif Private tags (See JEITA CP-3451C Section 4.6.8 Tag Support Levels)
    public static final List<String> IFD_EXIF_TAGS;

    // Primary image IFD GPS Info tags (See JEITA CP-3451C Section 4.6.6 Tag Support Levels)
    public static final List<String> IFD_GPS_TAGS;

    // Primary image IFD Interoperability tag (See JEITA CP-3451C Section 4.6.8 Tag Support Levels)
    public static final List<String> IFD_INTEROPERABILITY_TAGS;

    // IFD Thumbnail tags (See JEITA CP-3451C Section 4.6.8 Tag Support Levels)
    public static final List<String> IFD_THUMBNAIL_TAGS;

    // ORF file tags (See http://www.exiv2.org/tags-olympus.html)
    public static final List<String> ORF_MAKER_NOTE_TAGS;

    public static final List<String> ORF_CAMERA_SETTINGS_TAGS;

    public static final List<String> ORF_IMAGE_PROCESSING_TAGS;

    // PEF file tag (See http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/Pentax.html)
    public static final List<String> PEF_TAGS;

    static {
        IFD_TIFF_TAGS             = asList(INNER.IFD_TIFF_TAGS);
        IFD_EXIF_TAGS             = asList(INNER.IFD_EXIF_TAGS);
        IFD_GPS_TAGS              = asList(INNER.IFD_GPS_TAGS);
        IFD_INTEROPERABILITY_TAGS = asList(INNER.IFD_INTEROPERABILITY_TAGS);
        IFD_THUMBNAIL_TAGS        = asList(INNER.IFD_THUMBNAIL_TAGS);
        ORF_MAKER_NOTE_TAGS       = asList(INNER.ORF_MAKER_NOTE_TAGS);
        ORF_CAMERA_SETTINGS_TAGS  = asList(INNER.ORF_CAMERA_SETTINGS_TAGS);
        ORF_IMAGE_PROCESSING_TAGS = asList(INNER.ORF_IMAGE_PROCESSING_TAGS);
        PEF_TAGS                  = asList(INNER.PEF_TAGS);

        List<List<String>> lists = new ArrayList<>();
        lists.add(IFD_TIFF_TAGS);
        lists.add(IFD_EXIF_TAGS);
        lists.add(IFD_GPS_TAGS);
        lists.add(IFD_INTEROPERABILITY_TAGS);
        lists.add(IFD_THUMBNAIL_TAGS);
        lists.add(ORF_MAKER_NOTE_TAGS);
        lists.add(ORF_CAMERA_SETTINGS_TAGS);
        lists.add(ORF_IMAGE_PROCESSING_TAGS);
        lists.add(PEF_TAGS);
        EXIF_TAGS = Collections.unmodifiableList(lists);
    }

    // ============
    // = Exif Map =
    // ============

    /**
     * detail: EXIF Tag Group Value Map
     * @author Ttt
     * <pre>
     *     以 EXIF Tag Group 为分组进行获取各个 Group 值
     * </pre>
     */
    public static final class Group {

        public final Map<String, String> IFD_TIFF_TAGS             = new LinkedHashMap<>();
        public final Map<String, String> IFD_EXIF_TAGS             = new LinkedHashMap<>();
        public final Map<String, String> IFD_GPS_TAGS              = new LinkedHashMap<>();
        public final Map<String, String> IFD_INTEROPERABILITY_TAGS = new LinkedHashMap<>();
        public final Map<String, String> IFD_THUMBNAIL_TAGS        = new LinkedHashMap<>();
        public final Map<String, String> ORF_MAKER_NOTE_TAGS       = new LinkedHashMap<>();
        public final Map<String, String> ORF_CAMERA_SETTINGS_TAGS  = new LinkedHashMap<>();
        public final Map<String, String> ORF_IMAGE_PROCESSING_TAGS = new LinkedHashMap<>();
        public final Map<String, String> PEF_TAGS                  = new LinkedHashMap<>();
    }

    // =================
    // = ExifInterface =
    // =================

    /**
     * detail: 内部常量
     * @author Android
     */
    private static final class INNER {

        public static final  String TAG_THUMBNAIL_ORIENTATION            = "ThumbnailOrientation";
        private static final String TAG_EXIF_IFD_POINTER                 = "ExifIFDPointer";
        private static final String TAG_GPS_INFO_IFD_POINTER             = "GPSInfoIFDPointer";
        private static final String TAG_INTEROPERABILITY_IFD_POINTER     = "InteroperabilityIFDPointer";
        private static final String TAG_SUB_IFD_POINTER                  = "SubIFDPointer";
        private static final String TAG_ORF_CAMERA_SETTINGS_IFD_POINTER  = "CameraSettingsIFDPointer";
        private static final String TAG_ORF_IMAGE_PROCESSING_IFD_POINTER = "ImageProcessingIFDPointer";

        // Primary image IFD TIFF tags (See JEITA CP-3451C Section 4.6.8 Tag Support Levels)
        private static final String[] IFD_TIFF_TAGS = new String[]{
                // For below two, see TIFF 6.0 Spec Section 3: Bilevel Images.
                ExifInterface.TAG_NEW_SUBFILE_TYPE,
                ExifInterface.TAG_SUBFILE_TYPE,
                ExifInterface.TAG_IMAGE_WIDTH,
                ExifInterface.TAG_IMAGE_LENGTH,
                ExifInterface.TAG_BITS_PER_SAMPLE,
                ExifInterface.TAG_COMPRESSION,
                ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION,
                ExifInterface.TAG_IMAGE_DESCRIPTION,
                ExifInterface.TAG_MAKE,
                ExifInterface.TAG_MODEL,
                ExifInterface.TAG_STRIP_OFFSETS,
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.TAG_SAMPLES_PER_PIXEL,
                ExifInterface.TAG_ROWS_PER_STRIP,
                ExifInterface.TAG_STRIP_BYTE_COUNTS,
                ExifInterface.TAG_X_RESOLUTION,
                ExifInterface.TAG_Y_RESOLUTION,
                ExifInterface.TAG_PLANAR_CONFIGURATION,
                ExifInterface.TAG_RESOLUTION_UNIT,
                ExifInterface.TAG_TRANSFER_FUNCTION,
                ExifInterface.TAG_SOFTWARE,
                ExifInterface.TAG_DATETIME,
                ExifInterface.TAG_ARTIST,
                ExifInterface.TAG_WHITE_POINT,
                ExifInterface.TAG_PRIMARY_CHROMATICITIES,
                // See Adobe PageMaker® 6.0 TIFF Technical Notes, Note 1.
                TAG_SUB_IFD_POINTER,
                ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT,
                ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH,
                ExifInterface.TAG_Y_CB_CR_COEFFICIENTS,
                ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING,
                ExifInterface.TAG_Y_CB_CR_POSITIONING,
                ExifInterface.TAG_REFERENCE_BLACK_WHITE,
                ExifInterface.TAG_COPYRIGHT,
                TAG_EXIF_IFD_POINTER,
                TAG_GPS_INFO_IFD_POINTER,
                // RW2 file tags
                // See http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/PanasonicRaw.html)
                ExifInterface.TAG_RW2_SENSOR_TOP_BORDER,
                ExifInterface.TAG_RW2_SENSOR_LEFT_BORDER,
                ExifInterface.TAG_RW2_SENSOR_BOTTOM_BORDER,
                ExifInterface.TAG_RW2_SENSOR_RIGHT_BORDER,
                ExifInterface.TAG_RW2_ISO,
                ExifInterface.TAG_RW2_JPG_FROM_RAW,
                ExifInterface.TAG_XMP,
        };

        // Primary image IFD Exif Private tags (See JEITA CP-3451C Section 4.6.8 Tag Support Levels)
        private static final String[] IFD_EXIF_TAGS = new String[]{
                ExifInterface.TAG_EXPOSURE_TIME,
                ExifInterface.TAG_F_NUMBER,
                ExifInterface.TAG_EXPOSURE_PROGRAM,
                ExifInterface.TAG_SPECTRAL_SENSITIVITY,
                ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY,
                ExifInterface.TAG_OECF,
                ExifInterface.TAG_SENSITIVITY_TYPE,
                ExifInterface.TAG_STANDARD_OUTPUT_SENSITIVITY,
                ExifInterface.TAG_RECOMMENDED_EXPOSURE_INDEX,
                ExifInterface.TAG_ISO_SPEED,
                ExifInterface.TAG_ISO_SPEED_LATITUDE_YYY,
                ExifInterface.TAG_ISO_SPEED_LATITUDE_ZZZ,
                ExifInterface.TAG_EXIF_VERSION,
                ExifInterface.TAG_DATETIME_ORIGINAL,
                ExifInterface.TAG_DATETIME_DIGITIZED,
                ExifInterface.TAG_OFFSET_TIME,
                ExifInterface.TAG_OFFSET_TIME_ORIGINAL,
                ExifInterface.TAG_OFFSET_TIME_DIGITIZED,
                ExifInterface.TAG_COMPONENTS_CONFIGURATION,
                ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL,
                ExifInterface.TAG_SHUTTER_SPEED_VALUE,
                ExifInterface.TAG_APERTURE_VALUE,
                ExifInterface.TAG_BRIGHTNESS_VALUE,
                ExifInterface.TAG_EXPOSURE_BIAS_VALUE,
                ExifInterface.TAG_MAX_APERTURE_VALUE,
                ExifInterface.TAG_SUBJECT_DISTANCE,
                ExifInterface.TAG_METERING_MODE,
                ExifInterface.TAG_LIGHT_SOURCE,
                ExifInterface.TAG_FLASH,
                ExifInterface.TAG_FOCAL_LENGTH,
                ExifInterface.TAG_SUBJECT_AREA,
                ExifInterface.TAG_MAKER_NOTE,
                ExifInterface.TAG_USER_COMMENT,
                ExifInterface.TAG_SUBSEC_TIME,
                ExifInterface.TAG_SUBSEC_TIME_ORIGINAL,
                ExifInterface.TAG_SUBSEC_TIME_DIGITIZED,
                ExifInterface.TAG_FLASHPIX_VERSION,
                ExifInterface.TAG_COLOR_SPACE,
                ExifInterface.TAG_PIXEL_X_DIMENSION,
                ExifInterface.TAG_PIXEL_Y_DIMENSION,
                ExifInterface.TAG_RELATED_SOUND_FILE,
                TAG_INTEROPERABILITY_IFD_POINTER,
                ExifInterface.TAG_FLASH_ENERGY,
                ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE,
                ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION,
                ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION,
                ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT,
                ExifInterface.TAG_SUBJECT_LOCATION,
                ExifInterface.TAG_EXPOSURE_INDEX,
                ExifInterface.TAG_SENSING_METHOD,
                ExifInterface.TAG_FILE_SOURCE,
                ExifInterface.TAG_SCENE_TYPE,
                ExifInterface.TAG_CFA_PATTERN,
                ExifInterface.TAG_CUSTOM_RENDERED,
                ExifInterface.TAG_EXPOSURE_MODE,
                ExifInterface.TAG_WHITE_BALANCE,
                ExifInterface.TAG_DIGITAL_ZOOM_RATIO,
                ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM,
                ExifInterface.TAG_SCENE_CAPTURE_TYPE,
                ExifInterface.TAG_GAIN_CONTROL,
                ExifInterface.TAG_CONTRAST,
                ExifInterface.TAG_SATURATION,
                ExifInterface.TAG_SHARPNESS,
                ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION,
                ExifInterface.TAG_SUBJECT_DISTANCE_RANGE,
                ExifInterface.TAG_IMAGE_UNIQUE_ID,
                ExifInterface.TAG_CAMERA_OWNER_NAME,
                ExifInterface.TAG_BODY_SERIAL_NUMBER,
                ExifInterface.TAG_LENS_SPECIFICATION,
                ExifInterface.TAG_LENS_MAKE,
                ExifInterface.TAG_LENS_MODEL,
                ExifInterface.TAG_GAMMA,
                ExifInterface.TAG_DNG_VERSION,
                ExifInterface.TAG_DEFAULT_CROP_SIZE
        };

        // Primary image IFD GPS Info tags (See JEITA CP-3451C Section 4.6.6 Tag Support Levels)
        private static final String[] IFD_GPS_TAGS              = new String[]{
                ExifInterface.TAG_GPS_VERSION_ID,
                ExifInterface.TAG_GPS_LATITUDE_REF,
                // Allow SRATIONAL to be compatible with apps using wrong format and
                // even if it is negative, it may be valid latitude / longitude.
                ExifInterface.TAG_GPS_LATITUDE,
                ExifInterface.TAG_GPS_LONGITUDE_REF,
                ExifInterface.TAG_GPS_LONGITUDE,
                ExifInterface.TAG_GPS_ALTITUDE_REF,
                ExifInterface.TAG_GPS_ALTITUDE,
                ExifInterface.TAG_GPS_TIMESTAMP,
                ExifInterface.TAG_GPS_SATELLITES,
                ExifInterface.TAG_GPS_STATUS,
                ExifInterface.TAG_GPS_MEASURE_MODE,
                ExifInterface.TAG_GPS_DOP,
                ExifInterface.TAG_GPS_SPEED_REF,
                ExifInterface.TAG_GPS_SPEED,
                ExifInterface.TAG_GPS_TRACK_REF,
                ExifInterface.TAG_GPS_TRACK,
                ExifInterface.TAG_GPS_IMG_DIRECTION_REF,
                ExifInterface.TAG_GPS_IMG_DIRECTION,
                ExifInterface.TAG_GPS_MAP_DATUM,
                ExifInterface.TAG_GPS_DEST_LATITUDE_REF,
                ExifInterface.TAG_GPS_DEST_LATITUDE,
                ExifInterface.TAG_GPS_DEST_LONGITUDE_REF,
                ExifInterface.TAG_GPS_DEST_LONGITUDE,
                ExifInterface.TAG_GPS_DEST_BEARING_REF,
                ExifInterface.TAG_GPS_DEST_BEARING,
                ExifInterface.TAG_GPS_DEST_DISTANCE_REF,
                ExifInterface.TAG_GPS_DEST_DISTANCE,
                ExifInterface.TAG_GPS_PROCESSING_METHOD,
                ExifInterface.TAG_GPS_AREA_INFORMATION,
                ExifInterface.TAG_GPS_DATESTAMP,
                ExifInterface.TAG_GPS_DIFFERENTIAL,
                ExifInterface.TAG_GPS_H_POSITIONING_ERROR
        };
        // Primary image IFD Interoperability tag (See JEITA CP-3451C Section 4.6.8 Tag Support Levels)
        private static final String[] IFD_INTEROPERABILITY_TAGS = new String[]{
                ExifInterface.TAG_INTEROPERABILITY_INDEX
        };
        // IFD Thumbnail tags (See JEITA CP-3451C Section 4.6.8 Tag Support Levels)
        private static final String[] IFD_THUMBNAIL_TAGS        = new String[]{
                // For below two, see TIFF 6.0 Spec Section 3: Bilevel Images.
                ExifInterface.TAG_NEW_SUBFILE_TYPE,
                ExifInterface.TAG_SUBFILE_TYPE,
                ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH,
                ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH,
                ExifInterface.TAG_BITS_PER_SAMPLE,
                ExifInterface.TAG_COMPRESSION,
                ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION,
                ExifInterface.TAG_IMAGE_DESCRIPTION,
                ExifInterface.TAG_MAKE,
                ExifInterface.TAG_MODEL,
                ExifInterface.TAG_STRIP_OFFSETS,
                TAG_THUMBNAIL_ORIENTATION,
                ExifInterface.TAG_SAMPLES_PER_PIXEL,
                ExifInterface.TAG_ROWS_PER_STRIP,
                ExifInterface.TAG_STRIP_BYTE_COUNTS,
                ExifInterface.TAG_X_RESOLUTION,
                ExifInterface.TAG_Y_RESOLUTION,
                ExifInterface.TAG_PLANAR_CONFIGURATION,
                ExifInterface.TAG_RESOLUTION_UNIT,
                ExifInterface.TAG_TRANSFER_FUNCTION,
                ExifInterface.TAG_SOFTWARE,
                ExifInterface.TAG_DATETIME,
                ExifInterface.TAG_ARTIST,
                ExifInterface.TAG_WHITE_POINT,
                ExifInterface.TAG_PRIMARY_CHROMATICITIES,
                // See Adobe PageMaker® 6.0 TIFF Technical Notes, Note 1.
                TAG_SUB_IFD_POINTER,
                ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT,
                ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH,
                ExifInterface.TAG_Y_CB_CR_COEFFICIENTS,
                ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING,
                ExifInterface.TAG_Y_CB_CR_POSITIONING,
                ExifInterface.TAG_REFERENCE_BLACK_WHITE,
                ExifInterface.TAG_XMP,
                ExifInterface.TAG_COPYRIGHT,
                TAG_EXIF_IFD_POINTER,
                TAG_GPS_INFO_IFD_POINTER,
                ExifInterface.TAG_DNG_VERSION,
                ExifInterface.TAG_DEFAULT_CROP_SIZE
        };
        // ORF file tags (See http://www.exiv2.org/tags-olympus.html)
        private static final String[] ORF_MAKER_NOTE_TAGS       = new String[]{
                ExifInterface.TAG_ORF_THUMBNAIL_IMAGE,
                TAG_ORF_CAMERA_SETTINGS_IFD_POINTER,
                TAG_ORF_IMAGE_PROCESSING_IFD_POINTER
        };
        private static final String[] ORF_CAMERA_SETTINGS_TAGS  = new String[]{
                ExifInterface.TAG_ORF_PREVIEW_IMAGE_START,
                ExifInterface.TAG_ORF_PREVIEW_IMAGE_LENGTH
        };
        private static final String[] ORF_IMAGE_PROCESSING_TAGS = new String[]{
                ExifInterface.TAG_ORF_ASPECT_FRAME
        };
        // PEF file tag (See http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/Pentax.html)
        private static final String[] PEF_TAGS                  = new String[]{
                ExifInterface.TAG_COLOR_SPACE
        };
    }
}
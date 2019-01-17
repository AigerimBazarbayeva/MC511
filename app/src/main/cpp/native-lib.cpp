#include <jni.h>
#include <string>
#include "SGSmooth.h"

using namespace std;

extern "C" JNIEXPORT jdoubleArray JNICALL
Java_fh_ooe_mcm_inactivitytracker_utils_FeatureDerivator_smooth(
        JNIEnv* env,
        jobject /* this */,
        jdoubleArray data,
        jint width,
        jint degree) {

        jsize size = env->GetArrayLength( data );
        double* array = env->GetDoubleArrayElements(data, NULL);

        vector<double> dataToSmooth(array, array + size);
        vector<double> smoothedData = sg_smooth(dataToSmooth, width, degree);

        double* output = &smoothedData[0];

        jdoubleArray out = env->NewDoubleArray(size);

        env->SetDoubleArrayRegion(out, 0, size, output);

        env->ReleaseDoubleArrayElements(data,array,0);

    return out;
}

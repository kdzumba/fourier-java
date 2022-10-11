# Fourier Analysis
This program applies the concept of Fourier Analysis, particularly through the use of 
the discrete fourier transform to decompose images into sine and cosine components, and then 
uses those components to animate the original image construction process.

## Useful reference links on Fourier Analysis
1. https://www.youtube.com/watch?v=spUNpyF58BY&t=81s (3Blue1Brown)
2. https://www.youtube.com/watch?v=r18Gi8lSkfM (Physics Videos by Eugene Khutoryansky)
3. https://www.youtube.com/watch?v=MY4luNgGfms&t=2075s (The coding Train)
4. https://www.youtube.com/watch?v=iN0VG9N2q0U&t=959s (Better Explained)

## Overview of how the program works
1. Load some image points from an image source(Disk, URL)
2. Order the points such that they actually trace the image outline (Uses an implementation of the nearest neighbour algorithm)
3. Perform the Discrete Fourier Transform operation on the ordered list of image points
4. Generate some epicycles from the resulting Discrete-Time Fourier Transform values that come from the DFT operation
5. Animate the epicycles as they draw the original image
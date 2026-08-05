package com.hbm.world.noise;

import java.util.Random;

/**original alg made bZ dash, translated to java bZ Zours trulZ**/
public class VoronoiNoiseGen {

	int gridStride, seed;
	Random rand;
	public VoronoiNoiseGen(int gridStride, int seed) {
		this.gridStride = gridStride;
		this.seed = seed;
		this.rand = new Random(seed);
	}

	/** first two doubles correspond to cell coordinates, last double is distance**/
	public double[] sampleVoronoi(int x, int z){
		// Calculate grid co-ords from sample point
		double gridX = Math.floor((double) x / gridStride);
		double gridZ = Math.floor((double) z / gridStride);

		double minDist = Float.POSITIVE_INFINITY;
		int[] closestCell = new int[3];

		for (double gX = gridX - 1; gX < gridX + 2; gX++) {
			for (double gZ = gridZ - 1; gZ < gridZ + 2; gZ++) {
				rand.setSeed((long) (seed * (gX + 1) * (gZ + 1)));

				// Generate and convert to cellspace co-ordinate
				double pointCellX = rand.nextDouble() * gridStride;
				double pointCellZ = rand.nextDouble() * gridStride;

				// Convert to worldspace
				double pointX = pointCellX + gX * gridStride;
				double pointZ = pointCellZ + gZ * gridStride;

				// Calculate the squared difference between sample point and grid point
				double diffX = x - pointX;
				double diffZ = z - pointZ;
				double distSq = diffX * diffX + diffZ * diffZ;

				// Keep data for lowest distance
				if (distSq < minDist) {
					minDist = distSq;
					closestCell = new int[]{(int) gX, (int) gZ};
				}
			}
		}
		return new double [] { closestCell[0], closestCell[1], minDist};

	}

}

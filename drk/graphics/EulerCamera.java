package drk.graphics;

import drk.graphics.Camera;
import drk.Vector3D;
import javax.media.opengl.GL;
import java.lang.Math;


public class EulerCamera extends Camera
{
	public double yrotation,xrotation;
	public EulerCamera()
	{
		super();
		yrotation=xrotation=0.0;
	}
	
	public EulerCamera(Camera c)
	{
		super(c);
		Vector3D nyrot=new Vector3D(c.ZNormal.x,0.0,c.ZNormal.z);
		nyrot.enormal();
		yrotation=Math.toDegrees
		(
			Math.atan2(nyrot.x,nyrot.z)
		);
		
		xrotation=Math.toDegrees
		(
			Math.atan2
			(
				c.ZNormal.y,
				nyrot.mag()
			)
		);
		///\nxrot.enormal();
		
	}
	
	public void render(GL gl)
	{
		//GLU glu=new GLU();
		double[] crm=new double[16];
		
		gl.glRotated(-yrotation,0.0,1.0,0.0);
		gl.glRotated(-xrotation,1.0,0.0,0.0);
		gl.glTranslated(-Position.x,-Position.y,-Position.z);
		
		gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX,crm,0);
		
		XNormal.x=crm[0];	XNormal.y=crm[4];	XNormal.z=crm[8];
		YNormal.x=crm[1];	YNormal.y=crm[5];	YNormal.z=crm[9];
		ZNormal.x=-crm[2];	ZNormal.y=-crm[6];	ZNormal.z=-crm[10];
		
		
		
		/* note to self, with standard projection matrix (that is, -z goes inward)
		 * then the normalized camera components are stored in the modelview matrix as...if
		 * [[	i_x	i_y	i_z	Px'	]
		 *  [	j_x	j_y	j_z	Py'	]
		 *  [ 	-k_x	-k_y	-k_z	Pz'	]
		 *  [	0	0	0	1	]]
		 *  
		 *  where i,j,k are the unit vectors of the camera relative to world space,
		 *  and P' is equal to the U*P if U is the upper left 3x3 matrix here, and P 
		 *  is the world-space position vector
		 */
		
		
		
//		build the new camera rotation matrix from previous values..
		
		
		
		//rotation matrix is normalized camera normals along each axis, with a 0 translation vector
		
		
		//gl.glMultMatrixd()
		
		
	}
}

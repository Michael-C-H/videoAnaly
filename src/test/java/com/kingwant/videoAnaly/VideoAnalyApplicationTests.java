package com.kingwant.videoAnaly;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kingwant.videoAnaly.entity.VideoCamera;
import com.kingwant.videoAnaly.entity.VideoDressAbnormal;
import com.kingwant.videoAnaly.mapper.VideoDressAbnormalMapper;
import com.kingwant.videoAnaly.service.IVideoDressAbnormalService;



@RunWith(SpringRunner.class)
@SpringBootTest(classes=VideoAnalyApplication.class)
@ComponentScan(basePackages="com.kingwant")
public class VideoAnalyApplicationTests {

	@Test
	public void contextLoads() {
		
	}
	
	
	
	@Resource
	VideoDressAbnormalMapper vdam;
	
	@Test
	public void insertBlob() throws IOException, SerialException, SQLException {
		String path="d:/test/old/1.jpg";
		InputStream in = new FileInputStream(path);
		byte[] imgdata=InputStreamToByte(in);
		//Blob blob=new SerialBlob(imgdata);
		
		VideoDressAbnormal vd=new VideoDressAbnormal();
		vd.setCode("CH_test");
		vd.setExDate(new Date());
		vd.setId("ch1");
		vd.setImage(imgdata);
		vd.setName("ch测试");
		vd.setReason("异常原因1");
		
		vdam.insert(vd);
	}
	
	
	@Test
	public void selectBlob() throws IOException {
		
		String path="d:/test/new/new_1.jpg";
		VideoDressAbnormal vd=vdam.selectById("ch1");
		OutputStream out = new FileOutputStream(path);
        InputStream is = new ByteArrayInputStream(vd.getImage());
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
		
		
	}
	
	
	@Resource
	IVideoDressAbnormalService ivds;
	@Test
	public void testExCol() {
		
		Page<VideoDressAbnormal> page=new Page<>();
		page.setSize(1);
		page.setCurrent(1);
		Wrapper<VideoDressAbnormal> wrapper=new EntityWrapper<VideoDressAbnormal>();
		wrapper.eq("ID", "ch1");
		wrapper.setSqlSelect("ID AS id, CODE AS code, NAME AS name, EX_DATE AS exDate");
		
		Page<VideoDressAbnormal> list=ivds.selectPage(page, wrapper);
		
		System.err.println(list.getRecords().get(0));
	}
	
	
	private byte[] InputStreamToByte(InputStream is) throws IOException { 
		 
		  ByteArrayOutputStream bytestream = new ByteArrayOutputStream(); 
		  int ch; 
		  while ((ch = is.read()) != -1) { 
		    bytestream.write(ch); 
		  } 
		  byte imgdata[] = bytestream.toByteArray(); 
		  bytestream.close(); 
		 
		  return imgdata; 
		}

}


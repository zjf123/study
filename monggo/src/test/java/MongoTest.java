import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.gridfs.GridFSFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testapplicationContext.xml" })
public class MongoTest {
	private static final Log log = LogFactory.getLog(MongoTest.class);

	@Autowired
	MongoOperations mongoOps;

	@Autowired
	GridFsOperations operations;

	@Test
	public void testgrid() throws IOException {
		long start = System.currentTimeMillis();
		operations.delete(null);
		for (int i = 0; i < 10000; i++) {
			operations.store(new BufferedInputStream(new FileInputStream(
					"c:/input.jpg")), i + ".jpg");
		}
		System.out.println(System.currentTimeMillis() - start);
	}

	@Test
	public void test() throws IOException {
		long start = System.currentTimeMillis();
		operations.delete(null);
		for(int i=0;i<10000;i++){
			GridFsResource file =operations.getResource(i+".jpg");
			file.getInputStream();
		}
		System.out.println(System.currentTimeMillis()-start);
	}
}
